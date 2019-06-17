# PagerRecyclerView
支持整页滑动的 RecyclerView

![gif]("https://github.com/sirhu123/PagerRecyclerView/blob/master/app/gif/page-recyclerview.gif")

# 第一步：
- 按照正常流程创建 RecyclerView 并设置 RecyclerView.Adapter;
# 第二步：
- 根据 RecyclerView 的宽高控制每个 ItemView 的宽高；
# 第三步：
- PagerSnapHelper(itemCount : Int).attachToRecyclerView(rv : RecyclerView)；
- itemCount 为每页显示的 itemView 的个数；
