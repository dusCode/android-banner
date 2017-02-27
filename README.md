##### 自定义ViewGroup实现图片自动轮播
 * 继承ViewGroup
 * 重写onMeasure layout 方法
 * onMeasure: 根据子view的个数及宽高计算容器的宽高
 * layout:根据容器中子view是否发生变化，重新设置view的位置。left right top bottom 
 * 重写onInterceptTouchEvent方法，使父容器拦截事件。并重写onTouchEvent方法进行事件处理
 
#### 使用scroller或scrollTo scrollBy实现图片的滑动
     * scroller重写computeScroll方法

#### 使用Handler Timer及TimerTask实现图片自动轮播
     