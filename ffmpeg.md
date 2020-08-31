### 视频剪辑

```bash
# -ss 起始时间戳
# -to 结束时间戳
# -i 原始文件名
# -c copy 不用转码，直接复制
# 最后是输出文件名
# 这个命令速度跟复制文件差不多
# 在PotPlayer里可以用Ctrl+Shift+C复制时间戳，但是复制出来的不带毫秒。可以用Shift-Tab看当前带毫秒的时间戳
ffmpeg -ss 01:50.506 -to 30:51.182 -i "input.mp4" -c copy "output.mp4"
```

