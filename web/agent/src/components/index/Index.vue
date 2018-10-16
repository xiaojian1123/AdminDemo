<template>
    <div class="adminApp">
        <div class="side">
            <div class="logo"></div>
            <el-menu class="nav" :default-active="activeIndex" :unique-opened="true" :collapse="true"
                     background-color="#545c64" text-color="#fff" active-text-color="#409EFF">
                <el-submenu index="1">
                    <template slot="title">
                        <i class="iot-icon iot-icon-operator" style="margin-left: 4px"></i>
                        <span>操作员管理</span>
                    </template>
                    <el-menu-item index="1-1" @click="menuItemClick('/index/operator/operator')">
                        <i class="el-icon-tickets"></i>
                        <span slot="title">操作员管理</span>
                    </el-menu-item>
                    <el-menu-item index="1-2" @click="menuItemClick('/index/operator/role')">
                        <i class="el-icon-tickets"></i>
                        <span slot="title">角色管理</span>
                    </el-menu-item>
                    <el-menu-item index="1-3" @click="menuItemClick('/index/operator/log')">
                        <i class="el-icon-tickets"></i>
                        <span slot="title">日志管理</span>
                    </el-menu-item>
                </el-submenu>
                <el-menu-item index="2" @click="menuItemClick('/index/operator/log')">
                  <i class="el-icon-menu"></i>
                  <span slot="title">平台管理</span>
                </el-menu-item>
                <el-menu-item index="3" @click="menuItemClick('/index/operator/log')">
                  <i class="el-icon-mobile-phone"></i>
                  <span slot="title">游戏管理</span>
                </el-menu-item>
            </el-menu>
        </div>
        <div class="main">
            <div class="header">
                <div style="flex-grow: 1;">
                    <el-menu mode="horizontal" :default-active="activeHome">
                        <el-menu-item index="1" @click="home">首页</el-menu-item>
                    </el-menu>
                </div>
                <div>
                    <el-menu mode="horizontal" :default-active="activeRight">
                        <el-menu-item index="1" @click="openDialog('修改密码','changePassword')">修改密码</el-menu-item>
                        <el-menu-item index="2" @click="logout">退出系统</el-menu-item>
                    </el-menu>
                </div>
            </div>
            <div class="body">
                <keep-alive>
                    <router-view v-if="$route.meta.keepAlive"></router-view>
                </keep-alive>
                <router-view v-if="!$route.meta.keepAlive"></router-view>
            </div>
        </div>
        <el-dialog width="400px" :visible.sync="dialog.visible" :title="dialog.title" :close-on-click-modal="false"
                   :before-close="closeDialog">
            <component ref="dialog" @closeDialog="closeDialog" :is="dialog.view"></component>
            <div slot="footer" class="dialog-footer">
                <el-button @click="closeDialog">取 消</el-button>
                <el-button type="primary" @click="$refs.dialog.submit()">确 定</el-button>
            </div>
        </el-dialog>
    </div>
</template>

<script>
    import Empty from './Empty';
    import ChangePassword from './operator/operator/ChangePassword';

    export default {
        components: {
            Empty,
            ChangePassword
        },
        name: 'Index',
        data(){
            return {
                isCollapse: false,
                activeIndex: '2-1',
                activeHome:'1',
                activeRight:'',
                dialog: {
                    title: '',
                    visible: false,
                    view: ''
                }
            };
        },
        methods: {
            menuItemClick: function (__url) {
                this.$router.push(__url);
                this.activeHome = '2';
                this.activeRight = '0'
            },
            openDialog: function (__title, __view) {
                this.dialog.title = __title;
                this.dialog.view = __view;
                this.dialog.visible = true;
            },
            closeDialog: function () {
                this.dialog.view = 'empty';
                this.dialog.visible = false;
                this.activeRight = '1'
            },
            logout: function () {
                this.$ajax.post('admin/logout').then(() => {
                    window.sessionStorage.removeItem('admin');
                    window.location.reload();
                });
            },
            home:function () {
                this.$router.push('/index/home');
                this.activeHome = '1'
            }
        },
        created: function () {
            // this.activeIndex = this.$route.meta.id;
        }
    }
</script>

<style lang="scss">
    @import url('http://at.alicdn.com/t/font_581205_0nkk642jzf979zfr.css');

    .adminApp {
        display: flex;
        height: 100%;

        .side {
            display: flex;
            flex-direction: column;
            flex-shrink: 0;
            width: 64px;
            background-color: #545c64;
            overflow-x: hidden;

            .logo {
                flex-shrink: 0;
                padding: 0 15px;
                height: 60px;
            }

            .nav {
                flex-grow: 1;
                border-right: 0;
                overflow-y: auto;
                &::-webkit-scrollbar {
                    width: 0;
                    height: 0;
                }

                .el-menu--inline .el-submenu__title {
                    height: 50px;
                    line-height: 50px;
                }
            }
        }

        .main {
            display: flex;
            flex-direction: column;
            flex-grow: 1;
            overflow-x: hidden;

            .header {
                display: flex;
                flex-shrink: 0;
                padding: 0 15px;
                height: 61px;
                border-bottom: 1px solid #e6e6e6;
            }

            .body {
                flex-grow: 1;
                padding: 15px;
                width: 100%;
                background-color: #f2f2f2;
            }
        }

        h1, h2, h3, h4 {
            margin: 0;
        }

        .el-submenu, .el-menu-item {
            [class*=" iot-icon-"] {
                vertical-align: middle;
                margin-right: 5px;
                width: 24px;
                text-align: center;
                font-size: 18px;
            }
        }

        .el-card__header, .el-card__body {
            padding: 15px;
        }

        .el-card__header {
            .card-header {
                display: flex;

                .title {
                    padding-left: 5px;
                    flex-grow: 1;
                    line-height: 33px;
                    font-size: 18px;
                    border-left: 5px solid #409EFF;
                }

                .operate {
                    flex-shrink: 0;
                }

                form {
                    display: inline-block;
                }
                .el-form-item {
                    margin-bottom: 0;
                }
            }
        }

        .el-table {
            .el-button--mini {
                padding: 3px 10px;
            }

            .cell {
                line-height: 20px;
            }
        }

        .el-pagination {
            padding: 7px 7px 0;
        }

        .el-dialog__body {
            padding: 20px 20px 0;
            max-height: 600px;
            overflow-y: auto;
        }

        .el-dialog__footer {
            padding: 15px 20px;
        }

        form {
            .el-cascader, .el-select, .el-date-editor, .el-input-number, .el-autocomplete {
                width: 100%;
            }
        }
    }
</style>
