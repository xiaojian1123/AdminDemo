<template>
    <div>
        <el-collapse v-model="activeName" accordion>
            <el-collapse-item title="当前版本" name="1">
                <div>版本号: 1.0.0</div>
                <div>更新日期：2018-04-10</div>
            </el-collapse-item>
            <el-collapse-item title="历史版本" name="2">
                <div>版本号: 1.0.0</div>
                <div>更新日期：2018-04-10</div>
            </el-collapse-item>
        </el-collapse>
    </div>
</template>
<script>
    export default{
        name: 'home',
        data(){
            return{
                activeName: '1'
            }
        }
    }
</script>
<style>
    .el-collapse {
        border-top: 0;
    }

    .el-collapse-item__header {
        padding: 0 20px;
    }

    .el-collapse-item__content {
        padding: 0 40px;
    }
</style>
