<template>
    <div>
        <el-row style="height: 10px;">
        </el-row>

        <el-row >
            <el-button type="primary" round @click="addMenu">添加菜单</el-button>
        </el-row>
        <el-row style="height: 10px;"></el-row>

        <el-table
            :data="tableData"
            style="width: 100%; margin-bottom: 20px;"
            row-key="id"
            border
            :tree-props="{children: 'children', hasChildren: 'hasChildren'}">
            <el-table-column prop="urlName" label="菜单名称" sortable width="240"></el-table-column>
            <el-table-column prop="url" label="路由地址" sortable width="420"></el-table-column>
            <el-table-column prop="type" label="类型" sortable width="100">
                <template slot-scope="scope" >
                    <el-tag v-if="scope.row.type == 0">目录</el-tag>
                    <el-tag v-if="scope.row.type == 1" type="success">菜单</el-tag>
                    <el-tag v-if="scope.row.type == 2" type="warning">按钮</el-tag>
                </template>
            </el-table-column>
            <el-table-column prop="orderNum" label="排序" sortable width="80"></el-table-column>
            <el-table-column label="操作">
            <template slot-scope="scope">
                <el-button
                size="mini"
                @click="editMenu(scope.row)">编辑</el-button>
                <el-button
                size="mini"
                type="danger"
                @click="deleteMenu(scope.row)">删除</el-button>
            </template>
            </el-table-column>
        </el-table>



        <el-dialog
            title="菜单操作"
            :visible.sync="menudialogVisible"
            width="60%">
            
            <el-form :model="menuForm" ref="roleForm" label-width="100px" class="demo-ruleForm">
                <el-form-item label="菜单类型" prop="type">
                    <template>
                        <el-radio v-model="menuForm.type" :label="0">目录</el-radio>
                        <el-radio v-model="menuForm.type" :label="1">菜单</el-radio>
                        <el-radio v-model="menuForm.type" :label="2">按钮</el-radio>
                    </template>
                </el-form-item>
                <el-form-item label="菜单名称" prop="desc">
                    <el-input type="urlName" placeholder="请输入角色描述" v-model="menuForm.urlName" maxlength="200" show-word-limit></el-input>
                </el-form-item>
                <el-form-item label="路由路径" prop="desc">
                    <el-input type="url" placeholder="请输入路由路径" v-model="menuForm.url" maxlength="200" show-word-limit></el-input>
                </el-form-item>
                <el-form-item label="状态" prop="status">
                    <template>
                        <el-radio v-model="menuForm.status" :label="1">启用</el-radio>
                        <el-radio v-model="menuForm.status" :label="2">禁用</el-radio>
                    </template>
                </el-form-item>
                <el-form-item label="菜单顺序" prop="orderNum">
                    <el-input type="orderNum" placeholder="请输入角色描述" v-model="menuForm.orderNum" maxlength="200" show-word-limit></el-input>
                </el-form-item>
                <el-form-item label="上级菜单" prop="pidName">
                    <el-input type="pidName" placeholder="请输入角色描述" v-model="menuForm.pidName" maxlength="200" show-word-limit v-readonly= true @focus="opentoselectpid"></el-input>
                </el-form-item>
            </el-form>

            <span slot="footer" class="dialog-footer">
                <el-button @click="menudialogVisible = false">取 消</el-button>
                <el-button type="primary" @click="submitMenuForm">确 定</el-button>
            </span>
        </el-dialog>

        <el-dialog
            title="选择父菜单"
            :visible.sync="piddialogVisible"
            width="40%">
            <el-tree :data="data" :props="defaultProps"
                @node-click="PidNodeClick" 
                ></el-tree>
            <span slot="footer" class="dialog-footer">
                <el-button @click="piddialogVisible = false">取 消</el-button>
                <el-button type="primary" @click="piddialogVisible = false">确 定</el-button>
            </span>
        </el-dialog>

    </div>
    
</template>

<script>
export default {
    data(){
        return {
            tableData: [],
            menudialogVisible: false,
            piddialogVisible: false,
            menuForm: {
                type: 1,
                urlName: "name",
                url: "url",
                status: 1,
                orderNum: 1,
                pid: "default",
                pidName: ""
            },
            data: [{
                label: '用户管理',
                children: [{
                    label: '二级 1-1',
                    children: [{
                    label: '三级 1-1-1'
                    }]
                }]
            }],
            defaultProps: {
                children: 'children',
                label: 'urlName'
            },
        };
    },
    methods: {
        editMenu(row){
            this.http.request({
                    url: "menu/info/" + row.id,
                    method: "GET",
                })
                .then(({ data }) => {
                    if(data.code == 200){
                        console.log(data)
                        this.menuForm = data.data
                        this.menudialogVisible = true
                    };
                });
        },
        deleteMenu(row){
            this.$confirm('确认删除?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(() => {
                this.http({
                    url: "menu/remove/" + row.id,
                    method: "DELETE",
                }).then(({data}) => {
                    if(data.code == 200){
                        this.$message({
                            message: "删除菜单成功",
                            type: "success",
                        });
                        this.getMenuList();
                    }else{
                        // this.$message.error(data.msg);
                    }
            }) 
            }).catch(() => {
                this.$message({
                    type: 'info',
                    message: '已取消删除'
                });          
            });
        },
        getMenuList(){
            this.http
                .request({
                    url: "menu/tree",
                    method:"GET",
                }).then(({ data }) => {
                    this.tableData = data.data
                })
        },
        addMenu(){
            this.menudialogVisible = true;
        },
        opentoselectpid(){
            this.http
                .request({
                    url: "menu/tree",
                    method: "GET"
                }).then(({ data }) => {
                    // console.log(data);
                    this.data = data.data;
                });
            this.piddialogVisible = true;
        },
        getMenuTree(){

        },
        PidNodeClick(data, node, ztree){
            // console.log(data, node, ztree)
            this.menuForm.pidName = data.urlName
            this.menuForm.pid = data.id
        },
        submitMenuForm(){
            var operation = this.menuForm.id ? "update" : "save";

            this.http
                .request({
                    url: "menu/" + operation,
                    method: "POST",
                    data: this.menuForm
                }).then(({ data }) => {
                    // console.log(data)
                    if(data.code == 200){
                        var msg = "";
                        if(operation == "update"){
                            msg = "修改";
                        }else{
                            msg = "添加";
                        }
                        this.$message({
                                message: msg + "菜单成功",
                                type: "success",
                        });
                        this.menuForm =  {
                            type: 1,
                            urlName: "name",
                            url: "url",
                            status: 1,
                            orderNum: 1,
                            pid: "default",
                            pidName: ""
                        },
                        this.menudialogVisible = false,
                        this.getMenuList();
                    }
                });
        },
    },
    mounted(){
        this.piddialogVisible = false;
        this.menudialogVisible = false;
        this.getMenuList();
    },
};
</script>

<style>
</style>

