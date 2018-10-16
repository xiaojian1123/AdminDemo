<template>
    <el-form ref="form" label-width="80px" status-icon :model="model" :rules="rules">
        <el-form-item label="旧密码" prop="oldPassword">
            <el-input type="password" placeholder="请输入内容" v-model="model.oldPassword"></el-input>
        </el-form-item>
        <el-form-item label="新密码" prop="newPassword">
            <el-input type="password" placeholder="请输入内容" v-model="model.newPassword"></el-input>
        </el-form-item>
        <el-form-item label="确认密码" prop="confirmPassword">
            <el-input type="password" placeholder="请输入内容" v-model="model.confirmPassword"></el-input>
        </el-form-item>
    </el-form>
</template>

<script>
    export default{
        name: 'ChangePassword',
        data(){
            let validateConfirmPassword = (__rule, __value, __callback) => {
                if (__value !== this.model.newPassword) {
                    __callback(new Error('两次输入密码不一致!'));
                } else __callback();
            };
            return {
                rules: {
                    oldPassword: [
                        {required: true, message: '请输入内容'}
                    ],
                    newPassword: [
                        {required: true, message: '请输入内容'},
                        {min: 6, max: 12, message: '最小长度为6，最大长度为12'}
                    ],
                    confirmPassword: [
                        {required: true, message: '请输入内容'},
                        {min: 6, max: 12, message: '最小长度为6，最大长度为12'},
                        {
                            validator: validateConfirmPassword
                        }
                    ]
                },
                model: {
                    oldPassword: '',
                    newPassword: '',
                    confirmPassword: ''
                }
            };
        },
        methods: {
            submit: function () {
                this.$refs.form.validate((valid) => {
                    if (valid) {
                        this.$ajax.post('admin/changePassword', this.model).then((__resultData) => {
                            this.$message({
                                message: __resultData.msg,
                                type: 'success',
                                onClose: function () {
                                    window.sessionStorage.removeItem('admin');
                                    window.location.reload();
                                }
                            });
                            this.$emit('closeDialog');
                        }).catch((__error) => {
                            this.$message.error(__error);
                        });
                    }
                });
            }
        }
    }
</script>
