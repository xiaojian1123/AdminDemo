<template>
    <el-form ref="form" label-width="120px" status-icon :model="model" :rules="rules">
        <el-form-item label="角色名" prop="name">
            <el-input placeholder="请输入角色名" v-model="model.name"></el-input>
        </el-form-item>
        <el-form-item label="权限设定" prop="phone">
            <el-input placeholder="请选择权限值" v-model="model.permission" disabled onclick="permissionSelect"></el-input>
            <div id="menuTree" class="ztree" v-show=isShow></div>
        </el-form-item>
    </el-form>
</template>

<script>
    export default{
        name: 'SaveOrEdit',
        props: ['param'],
        data(){
            return {
                isShow:false,
                rules: {
                },
                model: {
                    id: '',
                    name:'',
                    permission:''
                },
            };
        },
        methods: {
            submit: function () {
                this.$refs.form.validate((valid) => {
                    if (valid) {
                        this.$ajax.post('operator/role/saveOrEdit', this.model).then((__resultData) => {
                            this.$message.success(__resultData.msg);
                            this.$emit('closeDialog', true);
                        }).catch((__error) => {
                            this.$message.error(__error);
                        });
                    }
                });
            },
            info: function (__id) {
                this.$ajax.get('operator/role/info',{id:__id}).then((__resultData) => {
                    this.model = __resultData.result;
                }).catch((__error) => {
                    this.$message.error(__error);
                })
            },
            permissionSelect:function () {
                this.$ajax.get('operator/role/listTree', function (__resultData) {
                    if (Boolean(__resultData) && __resultData.code === 0) {
                        // 设置图标跟链接地址为null，否则树显示会有问题
                        vm.treeObj = $.fn.zTree.init($('#menuTree'), {
                            check: {enable: true},
                            data: {
                                key: {name: 'name'}
                            }
                        }, __resultData.result);
                        if (typeof(__callbackFun) === 'function') __callbackFun();
                    }
                });
            }
        },
        created: function () {
            if(Boolean(this.param)){
                this.info(this.param);
            }
        }
    }
</script>
