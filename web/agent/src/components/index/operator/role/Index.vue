<template>
    <div>
        <el-card>
            <div slot="header">
                <div class="card-header">
                    <div class="title">角色管理</div>
                    <div class="operate">
                        <el-form :inline="true">
                            <el-form-item>
                                <el-button type="primary" @click="openDialog('添加角色','saveOrEdit','')">添加</el-button>
                                <el-button type="primary" @click="del('')" :disabled="!deletable">删除</el-button>
                                <el-button type="primary" @click="getDataList">刷新</el-button>
                            </el-form-item>
                        </el-form>
                    </div>
                </div>
            </div>
            <el-table :data="dataList" :max-height="tableMaxHeight" tooltip-effect="dark" @selection-change="tableSelect" border>
                <el-table-column type="selection" width="38"></el-table-column>
                <el-table-column type="index" width="45"></el-table-column>
                <el-table-column label="ID" prop="idx" width="150" >
                    <template slot-scope="scope">
                        <el-button type="text" @click="openDialog('修改角色','saveOrEdit',scope.row.idx)" class="keyword">{{scope.row.idx}}</el-button>
                    </template>
                </el-table-column>
                <el-table-column label="角色名" prop="name" width="100" ></el-table-column>
                <el-table-column label="权限" prop="permission" ></el-table-column>
                <el-table-column label="创建者" prop="creater" width="100" ></el-table-column>
                <el-table-column label="操作" width="150" fixed="right">
                    <template slot-scope="scope">
                        <el-button plain size="mini" type="danger" @click="del(scope.row.idx)">删除</el-button>
                    </template>
                </el-table-column>
            </el-table>
        </el-card>
        <el-dialog width="450px" :visible.sync="dialog.visible" :title="dialog.title" :close-on-click-modal="false"
                   :before-close="closeDialog">
            <component ref="dialog" @closeDialog="closeDialog" :is="dialog.view" :param="dialog.param"></component>
            <div slot="footer" class="dialog-footer">
                <el-button @click="closeDialog">取 消</el-button>
                <el-button type="primary" @click="$refs.dialog.submit()">确 定</el-button>
            </div>
        </el-dialog>
    </div>
</template>

<script>
import Empty from "../../Empty";
import SaveOrEdit from "./SaveOrEdit";

export default {
  components: {
    Empty,
    SaveOrEdit
  },
  name: "OperatorRole",
  data() {
    return {
      tableMaxHeight: document.documentElement.offsetHeight - 186,
      dataList: [],
      deletable: false,
      selectedList: [],
      dialog: {
        title: "",
        view: "",
        param: "",
        visible: false
      }
    };
  },
  methods: {
    getDataList: function() {
      this.$ajax.get("operator/role/listAll").then(__resultData => {
        let resultObj = __resultData.result;
        this.dataList = resultObj;
      });
    },
    openDialog: function(__title, __view, __param) {
      this.dialog.title = __title;
      this.dialog.view = __view;
      this.dialog.param = __param;
      this.dialog.visible = true;
    },
    closeDialog: function(__refresh) {
      this.dialog.view = "empty";
      this.dialog.visible = false;
      if (__refresh) {
        this.getDataList();
      }
    },
    del: function(__id) {
      this.$confirm("确认删除记录?", "提示", { type: "warning" })
        .then(() => {
          var ids = [];
          if (Boolean(__id)) {
            ids.push(__id);
          } else {
            this.selectedList.forEach(function(item) {
              ids.push(item.idx);
            });
          }
          console.log(ids.toString());
          this.$ajax
            .post("operator/role/del", { ids: ids })
            .then(__resultData => {
              this.$message.success(__resultData.msg);
              this.getDataList();
            })
            .catch(__error => {
              this.$message.error(__error);
            });
        })
        .catch(() => {});
    },
    tableSelect: function(__val) {
      this.selectedList = __val;
      this.deletable = this.selectedList.length > 0;
    }
  },
  created: function() {
    this.getDataList();
  }
};
</script>


<style>

</style>
