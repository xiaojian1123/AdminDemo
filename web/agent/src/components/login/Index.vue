<template>
  <div class="loginApp">
    <canvas id="canvas"></canvas>
    <div class="main">
      <div class="login">
        <div class="title"><span>游戏管理</span></div>
        <div class="sub-title">后台登录</div>
        <el-form ref="form" status-icon :model="model" :rules="rules">
          <el-form-item prop="account">
            <el-input v-model="model.account" placeholder="账号"></el-input>
          </el-form-item>
          <el-form-item prop="password">
            <el-input type="password" v-model="model.password" placeholder="密码"></el-input>
          </el-form-item>
          <el-button type="primary" @click="login">立即登录</el-button>
        </el-form>
      </div>
    </div>
  </div>
</template>

<script>
  import canvas from '../../assets/js/login_canvas';

  export default{
    name: 'Login',
    data(){
      return {
        rules: {
          account: {required: true, message: '请输入账号'},
          password: {required: true, message: '请输入密码'}
        },
        model: {
          account: '',
          password: ''
        }
      };
    },
    methods: {
      login: function () {
        //跳过登录
        sessionStorage.setItem('admin', 'admin');
        this.$router.push('/index/home');
        //登录验证
        this.$refs['form'].validate((valid) => {
          if (valid) {
            this.$ajax.post('admin/login', this.model).then((__resultData) => {
              let adminObj = __resultData.result;
              sessionStorage.setItem('admin', JSON.stringify(adminObj));
              this.$router.push('/index/home');
            }).catch((__error) => {
              this.$message.error(__error);
            });
          }
        });
      }
    },
    mounted: function () {
      document.onkeydown = (event) => {
        let e = event || window.event;
        if (e && e.keyCode === 13) {
          this.login();
        }
      };
      canvas.show();
    }
  }
</script>

<style lang="scss">
  .loginApp {
    position: relative;
    height: 100%;
    background: #232627;

    .main {
      display: flex;
      position: absolute;
      top: 0;
      left: 0;
      width: 100%;
      height: 100%;

      .login {
        margin: auto;
        padding: 25px 25px 50px;
        width: 450px;
        background-color: #fff;
        border-radius: 4px;
        opacity: 0.6;

        .title {
          text-align: center;
          font-size: 32px;

          span {
            display: block;
            position: relative;

            &:before, &:after {
              content: '';
              position: absolute;
              top: ceil(50%-2);
              width: 50px;
              height: 2px;
              background: #000;
            }

            &:before {
              left: 80px;
            }

            &:after {
              right: 80px;
            }
          }
        }

        .sub-title {
          line-height: 60px;
          font-size: 18px;
          color: #409EFF;
          text-align: center;
        }

        .el-input__inner, .el-button {
          height: 48px;
        }

        .el-button {
          display: block;
          width: 100%;
          font-size: 14px;
        }
      }
    }
  }
</style>
