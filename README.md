# PagerRecyclerView
支持整页滑动的 RecyclerView，详见[博客](https://juejin.im/post/5d07a3e6e51d4510803ce3b1)

<img src="https://github.com/sirhu123/PagerRecyclerView/blob/master/app/gif/page-recyclerview.gif" width="200" alt="gif"/>

# 第一步：
- 按照正常流程创建 RecyclerView 并设置 RecyclerView.Adapter；
# 第二步：
- 根据 RecyclerView 的宽高控制每个 ItemView 的宽高；
# 第三步：
- PagerSnapHelper(itemCount : Int).attachToRecyclerView(rv : RecyclerView)；
- itemCount 为每页显示的 itemView 的个数；
