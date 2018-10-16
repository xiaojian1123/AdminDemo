<template>
    <el-form ref="form" label-width="120px" status-icon :model="model" :rules="rules">
        <el-form-item label="ID" prop="id">
            <el-input placeholder="请输入操作员ID" :disabled="idDisabled" v-model="model.id"></el-input>
        </el-form-item>
         <el-form-item  v-if="!idDisabled" label="密码" prop="password">
            <el-input placeholder="请输入密码" v-model="model.password" ></el-input>
        </el-form-item>
        <el-form-item label="角色" prop="roleId">
            <el-select placeholder="请选择角色" v-model="model.roleId">
                <el-option v-for="item in roleList" :label="item.name" :value="item.idx" :key="item.idx"></el-option>
            </el-select>
        </el-form-item>
        <el-form-item label="联系方式" prop="phone">
            <el-input placeholder="请输入正确的联系方式" v-model="model.phone"></el-input>
        </el-form-item>
        <el-form-item label="地址" prop="addr">
            <el-input placeholder="请输入地址" v-model="model.addr"></el-input>
        </el-form-item>
    </el-form>
</template>

<script>
export default {
  name: "SaveOrEdit",
  props: ["param"],
  data() {
    return {
      rules: {
        id: [
          { required: true, message: "请输入内容" },
          { max: 64, message: "输入内容过长" },
          { pattern: /^[A-Za-z0-9_]+$/, message: "只能是字母、数字或下划线" },
          {
            validator: this.validator
          }
        ],
        roleId: [
          { required: true, message: "至少选择一个", trigger: "change" }
        ],
        password: [
          { required: true, message: "请输入内容" },
          { min: 6, max: 12, message: "最小长度为6，最大长度为12" },
          { pattern: /^\S+$/, message: "不能有空白符号" }
        ],
        phone: [
          {
            pattern: /^(((13)|(15)|(18))\d{9})$/,
            message: "请输入正确的电话号码"
          }
        ]
      },
      roleList: [],
      idDisabled: false,
      model: {
        id: "",
        roleId: "",
        phone: "",
        addr: ""
      }
    };
  },
  methods: {
    submit: function() {
      this.$refs.form.validate(valid => {
        if (valid) {
          this.$ajax
            .post("admin/saveOrEdit", this.model)
            .then(__resultData => {
              this.$message.success(__resultData.msg);
              this.$emit("closeDialog", true);
            })
            .catch(__error => {
              this.$message.error(__error);
            });
        }
      });
    },
    info: function(__id) {
      this.$ajax
        .get("admin/info", { id: __id })
        .then(__resultData => {
          this.model = __resultData.result;
        })
        .catch(__error => {
          this.$message.error(__error);
        });
    },
    getRole: function() {
      this.$ajax
        .get("operator/role/listAll")
        .then(__resultData => {
          this.roleList = __resultData.result;
        })
        .catch(__error => {
          this.$message.error(__error);
        });
    },
    validator: function(__rule, __value, __callback) {
      if (!this.idDisabled) {
        this.$ajax
          .get("admin/info", { id: __value })
          .then(() => {
            __callback(new Error("账号已存在"));
          })
          .catch(() => {
            __callback();
          });
      } else __callback();
    }
  },
  created: function() {
    this.getRole();
    if (Boolean(this.param)) {
      this.idDisabled = true;
      this.info(this.param);
    }
  }
};
</script>
