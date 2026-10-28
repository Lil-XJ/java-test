# 开发日志

## 修复 DrawCircle 图片加载 NPE（Panel.class 解析到 java.awt.Panel） · 2026-10-23 17:30

---

**需求**

`src/com/draw/DrawCircle.java` 里通过 `Toolkit.getImage(...)` 加载 `/icon.png` 并绘制，控制台报错，图片画不出来。排查图片路径写法还有没有问题。

**改动**

- `src/com/draw/DrawCircle.java`
  - `Panel.class.getResource("/icon.png")` → `MyPanel.class.getResource("/icon.png")`
  - 顺带把 `drawImage(img, 10, 10, 175,221,this)` 的逗号后补了空格（纯格式）

**设计原因 / 权衡**

根因不在路径字符串，而在 `getResource` 的宿主类：

- 文件顶部 `import java.awt.*`，所以 `Panel` 解析成 **`java.awt.Panel`**，不是自己写的类。
- JDK 类由 bootstrap classloader 加载（`Panel.class.getClassLoader() == null`），它看不到应用 classpath，`getResource("/icon.png")` 返回 `null`。
- 随后 `Toolkit.getImage(null)` 抛 NPE —— 这就是控制台的报错来源。

资源本身没问题：`src/icon.png` 会被 IDEA 拷到 `out/production/java-test/icon.png`，classpath 根下确实有 `/icon.png`。

改用自己类（`MyPanel.class` / `DrawCircle.class`）后由 AppClassLoader 查找，即可命中。之所以选 `MyPanel.class` 而不是 `DrawCircle.class`：资源是给 MyPanel 画的，用最近的宿主类语义更清晰；两者效果等价。

放弃的方案：用 `new ImageIcon("src/icon.png")` 这类基于文件系统的工作目录路径 —— 依赖运行时 cwd，换环境就碎；classpath 资源更稳。

**验证**

1. 对比实验（临时 `Check.java`）：
   - `Panel.class.getResource("/icon.png")` → `null`
   - `Check.class.getResource("/icon.png")` → `file:/D:/desktop/java-test/out/production/java-test/icon.png`
   - `Panel.class.getClassLoader()` → `null`（bootstrap），`Check.class` → `AppClassLoader`
2. 修复后 `javac -encoding UTF-8 -d out/production/java-test src/com/draw/DrawCircle.java` 编译通过。
3. 再跑一次资源探测（`Check2.java`，`Class.forName("com.draw.MyPanel")`）：
   - `MyPanel.class.getResource("/icon.png")` → 正确 URL
   - `Toolkit.getImage(url)` → 返回 `sun.awt.image.ToolkitImage`，不再 NPE
4. 临时验证文件已删除。

**遗留 / 待办**

- `Toolkit.getImage` 是**异步**加载：`paint` 第一次跑时图可能还没解码完，窗口会先空白，重绘一次才出现。要一次到位可改 `ImageIO.read(url)`（同步），或用 `MediaTracker` / `ImageIcon.getImage()` 等待加载完成。
- 图片每次 `paint` 都重新 `getImage`，浪费。应放到字段/静态字段里只加载一次。
- 规范上应覆写 `paintComponent(Graphics g)` 并调 `super.paintComponent(g)`，而不是覆写 `paint` —— `paint` 还负责边框/子组件，覆写它容易踩双缓冲和绘制顺序的坑。当前是练习代码，暂不强制改。
- 项目里 `dev-log/` 下已有旧的按日期拆分的 `2026-10-21.md`；按现行规则从本条起统一追加到 `DEV_LOG.md`，旧文件保留不动。

**关联**

- 未提交；工作区改动：`src/com/draw/DrawCircle.java`、`src/icon.png`（新增）

## 新增 Tank.java：Graphics2D 绘制坦克图形 · 2026-10-23 17:50

---

**需求**

参考一张青色小坦克截图（青色车身 + 绿色地面 + 深色背景），在项目里画一台类似的坦克图形。属于绘图练习，不改现有 DrawCircle。

**改动**

- 新增 `src/com/draw/Tank.java`
  - `Tank extends JFrame`：窗口 520x400，标题「坦克图形」，复用 `DrawCircle` 的 JFrame + 自定义 Panel 写法
  - `TankPanel extends JPanel`：把坦克拆成 7 个私有绘制方法，按层依次画
    - `drawBackground` / `drawGround`：深色底 + 绿色地面色带
    - `drawTracks`：圆角长条履带 + 两侧大负重轮 + 5 个小负重轮 + 履带板短线
    - `drawHull`：梯形车体（`Path2D`）+ 上沿高光 + 下缘暗部
    - `drawTurret`：圆角炮塔 + 上沿高光 + 车长舱盖
    - `drawBarrel`：炮管根部 / 管身 / 炮口制退器三段，指右
    - `drawDetails`：烟幕弹发射器、天线、侧面舱门、装饰线、车灯

**设计原因 / 权衡**

- **配色取自参考图**：`BODY #22D8D0`（青）、`GROUND #2EC82E`（绿）、`BG #2B2B2B`（深灰），另加 `BODY_DARK`/`BODY_LIGHT` 做亮暗面。纯单色平涂和参考图一样会「糊成一坨」，所以每个部件都叠了一层暗部/高光，层次才出得来。
- **拆成多个 draw* 方法而不是一个长 paint**：坦克部件多（履带、轮、车体、炮塔、炮管、细节），一个方法堆 100 多行没法看；拆开后每块形状有名字，改一处不会误伤其它。代价是方法数多一点，练习代码里值得。
- **这次覆写 `paintComponent` 并调 `super.paintComponent`**，没沿用 DrawCircle 里的 `paint`。上一条日志刚记过 `paint` 的坑，新代码直接用规范写法，顺便当正例。
- **开启 `KEY_ANTIALIASING`**：圆角矩形和圆轮子不开抗锯齿会有明显锯齿。
- **侧视、炮管朝右**：参考图分辨率太低（72px 左右的像素小人），车头朝向看不清；侧视是坦克最通用、最像「坦克」的画法，也方便讲解各部件。没做 3/4 俯视那种像素风，`Graphics2D` 矢量画像素风反而违和。
- **没用现成图片 / 没复用 DrawCircle 的 icon 加载**：需求是「绘制」，不是「贴图」；纯代码画才练得到 `Path2D` / `RoundRectangle2D` / 分层上色。

**验证**

1. `javac -encoding UTF-8 -d out/production/java-test src/com/draw/Tank.java` 编译通过。
2. 离屏渲染验证（临时 `RenderTank.java`，反射拿 `TankPanel` 画进 `BufferedImage` 存 PNG）：坦克成形，青色车身、深色履带负重轮、绿色地面、深色背景均符合预期，轮廓清晰可辨为侧视坦克。
3. 渲染过程踩到一个反射小坑：`TankPanel` 是包私有，`Method.invoke` 需要 `setAccessible(true)`，否则 `IllegalAccessException`。仅验证脚本的问题，与 Tank.java 本身无关。
4. 临时渲染脚本和 PNG 已删除，不留垃圾文件。

**遗留 / 待办**

- 参考图里坦克更「像素风 / 像素质感」，当前是平滑矢量风。若要像素风，需改用 `fillRect` 按像素网格硬画，或直接贴像素图。
- 负重轮、履带板坐标是硬编码的魔数，改窗口尺寸或整体缩放不会跟着变。练习够用；要通用得抽成「坦克中心点 + 缩放系数」或上 `AffineTransform`。
- 可以顺手加个键盘左右移动坦克的 demo（`KeyListener` + `repaint`），练习动画和重绘；这次没做。
- `Tank` 未接进 `DrawCircle`，两者是并列的独立示例。

**关联**

- 未提交；工作区改动：`src/com/draw/Tank.java`（新增）

## tankGame.drawTank：按 direct 绘制俯视 2D 坦克（四朝向） · 2026-10-24 10:20

---

**需求**

在 `src/tankGame/MyPanel.java` 的 `drawTank` 方法里生成坦克图形，要求：

- 俯视平面 2D（坦克大战那种顶视图），**不是**上次那个侧视立体坦克
- 按传入的 `direct` 参数绘制不同朝向

**改动**

- `src/tankGame/MyPanel.java` — 只改 `drawTank` 的 `switch(direct)` 分支，补全四个朝向：
  - `0-上` / `1-右` / `2-下` / `3-左`
  - 每个朝向由 4 块组成：左右两条履带 + 中间车体 + 炮塔圆 + 朝向炮管
  - 上/下包围盒 40x60，左/右包围盒 60x40
  - 补全了 javadoc：写明 `(x,y)` 是包围盒左上角、`direct` / `type` 取值含义
  - 原来的 `g.fill3DRect(x, y, 10, 60, false)` 改成 `g.fillRect`（见下「设计原因」）

**设计原因 / 权衡**

- **上一次做错在哪**：用户要的是 `tankGame` 包里 `drawTank` 的**俯视**坦克，我却在 `com/draw/Tank.java` 单独画了一台侧视立体坦克（车体高光、炮口制退器那套）。方向观完全不同——侧视是「从旁边看」，俯视是「从天上看」，后者才是坦克大战的游戏视角。这次严格画在 `drawTank` 里，不另开文件。
- **方向编码 0-上 1-右 2-下 3-左**：沿用坦克大战教程常见约定（顺时针），`MyPanel.paint` 里原本就传的 `direct=0`，保持兼容不破坏调用方。写进 javadoc 是因为 `direct` 是裸 `int`，不写清楚下次必然忘。
- **(x,y) 取包围盒左上角**：跟 `Tank` 现有的 `getX()/getY()` 语义一致，也跟原来 `fill3DRect(x, y, ...)` 的写法一致。代价是转向时包围盒尺寸变了（40x60 ↔ 60x40），左上角不动会导致转向时坦克「跳」一下。真正的游戏要用中心点 + `AffineTransform.rotate` 绕中心转才平滑，练习阶段先接受这点。
- **炮塔用 `fillOval`、履带/车体/炮管用 `fillRect`**：俯视图里炮塔是圆的、车身是方的，这三类形状区分就够了。没有给履带画负重轮/履带板——俯视 40px 宽的小坦克画那些细节全是糊的，反而看不出轮廓。
- **`fill3DRect` → `fillRect`**：用户明确要「平面 2D」，`fill3DRect` 会画出立体斜面高光，跟平面风格冲突。四个朝向统一用 `fillRect`，观感干净。
- **炮管用 `fillRect` 而不是 `drawLine`**：`drawLine` 只有 1px，缩放后几乎看不见；4px 实心矩形炮管在任何缩放下都清晰。宽度 4 是试出来的——比 3 厚实、比 5 不显笨。
- **没把 `direct` 抽成常量/枚举**：`Tank`/`MyTank` 目前都是裸 int 风格，加枚举会跟周围代码不协调。等方向逻辑变复杂（比如子弹也吃 direct）再抽不迟。

**验证**

1. `javac` 编译 `tankGame` 四个类（`Tank` / `MyTank` / `MyPanel` / `TankGame`）通过。
2. 离屏渲染（临时 `Render4.java`，反射调 `drawTank` 把 4 个朝向画进一张 PNG）：
   - `0-上`：履带竖放，炮管朝上 ✓
   - `1-右`：履带横放，炮管朝右 ✓
   - `2-下`：履带竖放，炮管朝下 ✓
   - `3-左`：履带横放，炮管朝左 ✓
   - 四个都能辨认出「履带 + 车体 + 炮塔 + 炮管」，朝向正确
3. 临时渲染脚本和 PNG 已删除。

**遗留 / 待办**

- **转向会跳**：见上文，包围盒左上角锚定导致转向时位置偏移。要平滑转向需改为「中心点锚定 + `AffineTransform.rotate`」。
- `MyPanel.paint` 里 `direct` 写死为 `0`、`type` 写死为 `1`（红色，敌方色）。等 `Tank` 加上 `direct` 字段、接上键盘事件后，这里应改成 `drawTank(myTank.getX(), myTank.getY(), g, myTank.getDirect(), 0)`。
- `Tank` 类目前只有 `x` / `y`，**还没有 `direct` 字段**。`drawTank` 签名已经接受 `direct`，等做移动/转向时需补 `getDirect()/setDirect()`。
- `type` 只处理了 0（青）/1（红），`default` 分支会静默不画。要加第三方阵营（比如子弹、道具）时补 `case` 或加 `default` 兜底色。
- `paint` 仍覆写的 `paint` 而非 `paintComponent`（上次日志提过），`fillRect(0,0,1000,750)` 的黑底也超过窗口实际 400x300。属于既有问题，本次按「只画坦克」的范围没动。

**关联**

- 未提交；工作区改动：`src/tankGame/MyPanel.java`

## MyPanel.drawTank 加层叠质感：分层明暗 + 旋转复用 + 抗锯齿 · 2026-10-24 11:05

---

**需求**

上一版坦克是纯色平涂，四块形状糊在一起看不出结构。要求「美观一点，做出层级」——各部件要有前后/上下叠压关系，能一眼看出履带垫底、炮塔盖顶。

**改动**

- `src/tankGame/MyPanel.java`
  - `drawTank` 重构：不再为四个朝向各写一份坐标，改成**绕包围盒中心旋转**复用同一套「炮口朝上」画法
  - 新增 `drawTankUp(Graphics2D, Color base)`：单方向的完整分层绘制
  - 新增 `mix(Color a, Color b, float t)`：从阵营基色派生整条明暗梯度
  - `paint` 的背景从默认黑改为 `#2B2F2B` 深灰绿，不然影子画在纯黑上看不见
  - 引入 `Graphics2D`、`AffineTransform`、`Ellipse2D`、`RoundRectangle2D`，开抗锯齿

**设计原因 / 权衡**

- **层级怎么做的**：画笔叠放顺序 = 影子 → 履带 → 车体 → 炮管 → 炮塔，越靠上越亮。关键一处：**炮管先画、炮塔后画**，炮管根部自然被炮塔盖住，看起来就是「从炮塔里伸出来」，而不是两块独立图形拼在一起。这是靠绘制顺序而非描边伪造的深度。
- **明暗不能手写色号**：手写十几个颜色值，换阵营（青/红）就得重写一遍。改成先取基色 → 压暗 20% 得中间调 `mid` → 再往黑/白各拉几档派生全部零件色。这样青色、红色、灰色任意基色都自动有完整梯度，新增阵营零成本。
- **亮度梯度要拉开**：最暗（履带描边，往黑 62%）到最亮（炮塔高光，往白 80%）跨了整条明度轴。如果几层挤在同一亮度上，叠放顺序画了也看不出来——「有层级」的本质是明度差，不只是形状差。
- **四朝向改旋转复用**：上一版四个 `case` 各写一份 `fillRect` 坐标，加一个细节（比如履带横纹）要改四处，必然改漏。现在只写一份「朝上」画法，`g2.translate(中心) + g2.rotate(theta)` 得到其余三向。代价是多一次矩阵变换，换来细节绝对一致。旋转中心取包围盒中心（上/下 40x60 → 中心 `(x+20,y+30)`，左/右 60x40 → `(x+30,y+20)`），这样朝向切换时坦克不位移。
- **必须 `setTransform` 复原**：`drawTank` 每帧会被调用 N 次（我方 + 敌方若干），矩阵不还原会层层叠加越转越歪。用 `getTransform()` 存、`setTransform()` 还原，抗锯齿设置同理存还原，避免影响后续绘制。
- **抗锯齿**：`Ellipse2D` / `RoundRectangle2D` 不开 AA 锯齿很明显，尤其是炮塔同心圆。开完质感立马上一个档次，这是「美观」里性价比最高的一步。
- **光源统一在左上**：炮塔高光点放左上，影子往右下偏 2~3px。光源不统一的话，部件之间会互相矛盾，反而比没影子更假。
- **背景色改动**：原 `fillRect` 没 `setColor`，继承的是默认黑。影子是半透明黑（alpha 70），画在纯黑上完全不可见，层级就少了一环。改成深灰绿战场色，影子才显出来。

**验证**

1. `javac` 编译 `tankGame` 四个类通过。
2. 离屏渲染 PNG 两张（临时脚本）：
   - **四朝向 × 两阵营**共 8 台：炮管分别朝上/右/下/左，青红两色明暗梯度都成立，旋转后细节一致
   - **单台 3 倍放大**：能清楚分辨履带描边 → 履带主体 → 履带横纹 → 车体三层 → 炮管 + 高光条 → 炮塔四层同心圆 + 左上高光点，炮管根部确实在炮塔下面
3. 临时渲染脚本和 PNG 已删除。

**遗留 / 待办**

- 影子目前是固定圆角矩形，不是坦克真实轮廓。俯视小尺寸下够用；要更讲究得把车体轮廓也镜像一份做投影。
- `drawTankUp` 里十几个色变量是局部变量，每帧调用都重新算一遍 `mix`。量小无所谓；等同屏几十台坦克再考虑提到静态常量或缓存。
- `paint` 仍是覆写 `paint` 而非 `paintComponent`，背景 `fillRect(0,0,1000,750)` 也超过窗口 400x300。这次动了 `paint` 的背景色，顺手换成 `paintComponent` + `getWidth()/getHeight()` 更干净，但会牵动 `super.paint` 的语义，留到接键盘控制时一起改。
- 旋转复用意味着**转向瞬间不会「跳」**了（中心点固定），但 `(x,y)` 仍是包围盒左上角语义，和 `Tank` 的存取字段一致，没改 `Tank`。

**关联**

- 未提交；工作区改动：`src/tankGame/MyPanel.java`

## 修复 hitTank 命中判定只查单轴、隔空命中 · 2026-10-28 09:54

---

**需求**

检查「坦克子弹击中敌人」的判断逻辑（`MyPanel.hitTank`）：命中条件有问题，会出现子弹刚出膛就把远处敌人判死的隔空命中。

**改动**

- `src/tankGame/MyPanel.java`
  - `hitTank` 重写为统一的矩形重叠（AABB）判断：两个轴都相交才算命中
  - 朝向只用来取包围盒宽高（上/下 40x60、左/右 60x40），不再决定「查哪个轴」
  - 子弹按 5x5 方块参与重叠（画出来就是 `fill3DRect(...,5,5)`），不再只取 `(s.x, s.y)` 一个点
  - 补上 javadoc，说明包围盒尺寸和「为什么必须两轴都查」
  - 移除 227 行一条残缺的 `enemyTanks` 语句（编辑残留，本身就是编译错误）

**设计原因 / 权衡**

- 根因：原判断只查**垂直于坦克朝向**的一个轴（上/下朝向只查 `s.x ∈ [x, x+40]`，左/右只查 `s.y ∈ [y, y+40]`），行进方向的轴完全没参与比较。只要 x（或 y）对齐，子弹刚出膛、敌人在屏幕另一头也会立刻「命中」。想判断子弹有没有**到达**坦克，行进轴的比较不可省——单轴判断只是 AABB 的一半。
- 命中本质是两个矩形是否重叠，与朝向无关；朝向只影响包围盒形状（旋转后 40x60 ↔ 60x40）。所以收敛成一个布尔 + 三目取 w/h，四套 case 合并成一条式子，以后改尺寸只动一处。
- 边界统一为开区间重叠（`s.x + 5 > ex && s.x < ex + w`）：子弹与坦克边界贴住不算命中，视觉上更接近「打进去了才算」，同时消掉原来 `>=/<=` 与 `>/<` 两套标准并存的不一致。
- 顺带修掉一处不一致：原 `case 1/3` 命中不生成 `Bomb`，左右朝向的敌人被打中没爆炸；现在命中一律加爆炸。
- 放弃的方案：保留 switch 分支、只补缺失那根轴的比较——能修主 bug，但四份边界代码迟早再次改漏，Bomb 不一致的坑也还在。

**验证**

1. `javac -encoding UTF-8 -d /tmp/tankcheck src/tankGame/*.java` 编译通过（清掉残句后全文件）。
2. 边界推演（敌人 (100,10) 朝下，包围盒 x∈[100,140]、y∈[10,70]）：
   - 子弹 (120, 700) 向上飞：旧逻辑出膛即命中（120 在 x 范围内就杀）；新逻辑 `s.y=700` 不在 y 范围 → 不命中，飞到 `s.y < 70` 才命中。
   - 子弹 x=90（右缘 95 < 100）不命中；x=120 在 x 范围内且 y 相交 → 命中。
3. 未做 GUI 实机回归（无自动化测试），建议跑一局确认手感。

**遗留 / 待办**

- `hitTank` 只处理「我方子弹 vs 敌方坦克」；敌方子弹（`EnemyTank.shots`）目前**不与我方坦克做碰撞**，打不中人。要做可复用同一套 AABB，把 `MyTank`（`Tank.isLive` 字段已在）传进去。
- 子弹速度 2px/50ms、检测周期 100ms，一跳约 4px，远小于包围盒 40px，不会穿透；以后若大幅提速需改扫掠（swept）检测或缩短检测周期。
- `Bomb` 生成在坦克包围盒左上角，爆炸图 60x60 画在 `(x, y)`，对 40x60 的坦克没对中。要居中可改 `(x + w/2 - 30, y + h/2 - 30)`。
- 40/60/5 这些尺寸是散落在 `hitTank` / `drawTank` / `MyTank.shot` 里的魔法数，等加第二种坦克尺寸时应收敛到 `Tank` 的常量。

**关联**

- 未提交；工作区改动：`src/tankGame/MyPanel.java`（同期还有把 `enemyTanks` 改 `static` 的编辑，非本次改动但已一并编译验证）
