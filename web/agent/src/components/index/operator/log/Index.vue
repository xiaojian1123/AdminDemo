<template>
    <div>
        <el-card>
            <div slot="header">
                <div class="card-header">
                    <div class="title">操作员日志</div>
                    <div class="operate">
                        <el-form :inline="true">
                            <el-form-item>
                                <el-button type="primary" @click="getDataList">刷新</el-button>
                            </el-form-item>
                        </el-form>
                    </div>
                </div>
            </div>
            <el-form :inline="true">
                <el-form-item>
                    <el-select placeholder="请选择操作员" :clearable="true" v-model="params.operatorId">
                        <el-option v-for="item in operators" :label="item.id" :value="item.id" :key="item.id"></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item>
                    <el-date-picker
                        v-model="params.time"
                        type="daterange"
                        :editable="false"
                        start-placeholder="开始日期"
                        end-placeholder="结束日期"
                        :picker-options="pickerOptions">
                    </el-date-picker>
                </el-form-item>
                <el-form-item>
                    <el-input placeholder="请输入日志详情" v-model="params.keyword" style="width: 200px"></el-input>
                </el-form-item>
                <el-form-item>
                    <el-button type="primary" @click="getDataList">查找</el-button>
                </el-form-item>
            </el-form>
            <el-table :data="dataList" :max-height="tableMaxHeight" tooltip-effect="dark" border>
                <el-table-column type="index" width="45"></el-table-column>
                <el-table-column label="操作员" prop="operatorId" width="150">
                </el-table-column>
                <el-table-column label="日志详情" prop="description"></el-table-column>
                <el-table-column label="时间" prop="utc" :formatter="utcFormatter"></el-table-column>
            </el-table>
            <el-pagination @size-change="pageSizeChange" @current-change="pageCurrentChange"
                           :current-page="params.pageNum" :page-size="params.pageSize"
                           :total="params.totalCount" :page-count="params.pageCount"
                           :page-sizes="[30, 50, 100, 200]" layout="total, sizes, prev, pager, next, jumper">
            </el-pagination>
        </el-card>
    </div>
</template>

<script>
import ElInput from "../../../../../node_modules/element-ui/packages/input/src/input.vue";

export default {
  components: { ElInput },
  name: "OperatorLog",
  data() {
    return {
      tableMaxHeight: document.documentElement.offsetHeight - 186,
      dataList: [],
      operators: [],
      params: {
        operatorId: "",
        time: "",
        pageNum: 1,
        pageSize: 30,
        totalCount: 0,
        pageCount: 0
      },
      pickerOptions: {
        shortcuts: [
          {
            text: "最近一周",
            onClick(picker) {
              var end = new Date();
              var start = new Date();
              start.setTime(start.getTime() - 3600 * 1000 * 24 * 7);
              picker.$emit("pick", [start, end]);
            }
          },
          {
            text: "最近一个月",
            onClick(picker) {
              var end = new Date();
              var start = new Date();
              start.setTime(start.getTime() - 3600 * 1000 * 24 * 30);
              picker.$emit("pick", [start, end]);
            }
          },
          {
            text: "最近三个月",
            onClick(picker) {
              var end = new Date();
              var start = new Date();
              start.setTime(start.getTime() - 3600 * 1000 * 24 * 90);
              picker.$emit("pick", [start, end]);
            }
          }
        ]
      }
    };
  },
  methods: {
    getDataList: function() {
      var param = {
        start: Boolean(this.params.time) ? this.params.time[0].getTime() : null,
        end: Boolean(this.params.time) ? this.params.time[1].getTime() : null,
        operatorId: this.params.operatorId,
        keyword: this.params.keyword,
        pageSize: this.params.pageSize,
        pageNum: this.params.pageNum
      };
      this.$ajax.get("operator/log/listPage", param).then(__resultData => {
        let resultObj = __resultData.result;
        if (resultObj.list.length === 0 && this.params.pageNum > 1) {
          this.params.pageNum--;
          this.getDataList();
          return;
        }
        this.dataList = resultObj.list;
        this.params.pageCount = resultObj.pageCount;
        this.params.totalCount = resultObj.totalCount;
      });
    },
    getOperators: function() {
      this.$ajax.get("admin/listAll").then(__resultData => {
        let resultObj = __resultData.result;
        this.operators = resultObj;
        this.operators.push(JSON.parse(sessionStorage.getItem("admin")));
      });
    },
    utcFormatter: function(__row) {
      return this.$utils.utcFormat(__row.utc);
    },
    pageSizeChange: function(__pageSize) {
      this.params.pageSize = __pageSize;
      this.getDataList();
    },
    pageCurrentChange: function(__currentPage) {
      this.params.pageNum = __currentPage;
      this.getDataList();
    }
  },
  created: function() {
    this.getOperators();
    this.getDataList();
  }
};
</script>


<style>

</style>
