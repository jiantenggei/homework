<template>
  <div style="margin-top: 0px">
    <el-container style="background: aliceblue">
      <p>
        <span style="font-size: 40px;">豆瓣电影排名250</span></p>
      <el-header >
        <div align="center">
          <el-row :gutter="10">
            <el-col :span="0.5">
              <div class="grid-content ">
              </div>
            </el-col>
            <el-col :span="5">
              <div class="grid-content">
                <el-input v-model="input" placeholder="请输入电影名字" style="margin-top: 20px"></el-input>
              </div>
            </el-col>
            <el-col :span="1">
              <div class="grid-content">
                <el-button type="primary" style="margin-top: 20px;margin-left: auto " @click="search">搜索</el-button>
              </div>
            </el-col>
            <el-col :span="2">
              <div class="grid-content">
                <el-button type="danger" style="margin-top: 20px;margin-left: auto " @click="reset">重置</el-button>
              </div>
            </el-col>
            <el-col :span="100">

              <div class="grid-content">
                <el-link type="danger" style="font-size: 20px;margin-top: 25px">注意：查询后不展示是因为没有查询结果！！</el-link>
              </div>
            </el-col>
          </el-row>
        </div>
      </el-header>
      <el-main style="margin-left: 0px">
        <div class="demo-image">
          <el-row :gutter="30">
            <div class="block" v-for="(movie,index) in Movie" :key="index">
              <el-col :span="6">
                <el-image
                  style="width: 250px; height: 250px"
                  :src=getImages(movie.moviePicture)
                  fit="fill"></el-image>

              <div class="namespace"  >
              <a>排名：{{ movie.movieTop | filtername }}</a> <br>
              <a>电影名称：{{ movie.movieCname | filtername }}</a><br>
              <a style="">外文名：{{ movie.movieFname | filtername }}</a><br>
               评分: <el-rate
                  v-model = "movie.movieScore/2"
                  allow-half
                  disabled
                  >
                </el-rate> <a style="color: crimson;font-size: larger">{{movie.movieScore}}</a>
             <br>
              <a>参评人数：{{ movie.movieComentNum | filtername }}</a><br>
                <el-popover
                  placement="top-start"
                  title="简介"
                  width="20"
                  trigger="hover"
                  :content="movie.movieDetail">
                  <el-button slot="reference">详情</el-button>
                </el-popover>
             </div>
              </el-col>
            </div>
            <br>
          </el-row>
        </div>
        <br>
        <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange"
                       :current-page="pageNo" :page-sizes="[4, 8,16,24]" :page-size="pageSize"
                       layout="total, sizes, prev, pager, next, jumper" :total="total">
        </el-pagination>
      </el-main>
      <el-footer>
        <Footer></Footer>
      </el-footer>
    </el-container>
  </div>
</template>

<script>
import Header from "./Header";
import Footer from "./Footer";

export default {
  components: {Footer, Header},
  name: "Main",
  data() {
    return {
      Movie: {},
      pageNo: 1,
      total: null,
      pageSize: 8,
      input: "",
      value: 5.0,
      max:10,
      colors: ['#99A9BF', '#F7BA2A', '#FF9900']
    };
  },
  filters:{
    filtername(val){
      if(!val){
        return "空"
      }
      return  val
    }
  },

  methods: {
    getImages(_u){
        return 'https://images.weserv.nl/?url=' + _u;
      },
    getd(){
      return 3.6
    },
    getPage() {
      this.$axios.post('http://329775f22d.qicp.vip/movie/getMovies', {
        pageNo: this.pageNo,
        pageSize: this.pageSize,
        movieName: this.input
      }).then(res => {
          if (res.data.code == "SUCCESS") {
            this.Movie = res.data.data.data
            console.log(this.Movie)
            this.pageNo = res.data.data.pageNumber
            this.total = res.data.data.totalElements
            this.pageSize = res.data.data.pageSize
          } else {
            this.$notify({
              type: "warning",
              message: res.data.message
            })
          }
        }
      )
    },
    handleSizeChange(val) {
      console.log(`每页 ${val} 条`);
      this.pageSize = val
      this.getPage()
    },
    // 当当前页改变
    handleCurrentChange(val) {
      console.log(`当前页: ${val}`);
      this.pageNo = val
      this.getPage()
    },
    search() {
      console.log(this.input)
      this.pageNo = 1
      this.getPage()
    },
    reset() {
      this.pageNo = 1
      this.pageNo = 1
      this.total = null
      this.pageSize = 8,
        this.input = ""
      this.getPage()
    }
  },
  created(){
    this.getPage()

  }
}
</script>

<style scoped>
.namespace{
  text-align: left;
  margin-bottom: 10px;
  height: 180px;
  width: 250px;
  padding-left: 50px;
}
.el-rate{
  display: inline;
}

</style>
