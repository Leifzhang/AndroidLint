# AndroidLint

Android 自定义lint教学

主要功能罗列下:

1. EventSpaceDetector Event 传入参数是否含有空格扫描
2. GlideDetector 不允许直接使用glide 以及直接用BitmapFactory
3. LogDetector  不允许直接使用Log
4. PngResourceDetector 扫描Png 大图检查 扫描的res文件
5. RouteDetector 不允许项目内直接使用路由 
6. ThreadDetector 这个是别人写的 线程构造的检查


## 支持动态拔插的能力

通过SPI机制，可以允许自定义lint规则，然后动态化合并到Lintcheck中，增加lint的可拓展性。
