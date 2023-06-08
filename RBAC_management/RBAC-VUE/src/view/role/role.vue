<template>
    <div>

        <el-row :gutter="20">
            <el-col :span="3"><div class="grid-content bg-purple">角色名称：</div></el-col>
            <el-col :span="6"><div class="grid-content bg-purple">
                <el-input v-model="queryParam.roleName" placeholder="请输入内容"></el-input>
            </div></el-col>
            <el-col :span="3"><div class="grid-content bg-purple">角色描述：</div></el-col>
            <el-col :span="6"><div class="grid-content bg-purple">
                <el-input v-model="queryParam.roleDesc" placeholder="请输入内容"></el-input>
            </div></el-col>
            <el-col :span="2"><div class="grid-content bg-purple">
                <el-button type="primary" round @click="searchrole" :disabled="findrolebuttonDisabled">搜索</el-button>
            </div></el-col>
            <el-col :span="1"><div class="grid-content bg-purple">
                <el-button type="primary" round @click="resetsearch">重置</el-button>
            </div></el-col>
        </el-row>

        <el-row style="height: 10px;">
        </el-row>

        <el-row >
            <el-button type="primary" round @click="addRole" :disabled="addrolebuttonDisabled">添加角色</el-button>
        </el-row>
        <el-row style="height: 10px;"></el-row>

        <el-table :data="tableData" border style="width: 100%">
        <el-table-column prop="id" label="角色ID" width="80"></el-table-column>    
        <el-table-column prop="roleName" label="角色名" width="180"></el-table-column>
        <el-table-column prop="roleDesc" label="角色描述" style = "width: 30%"></el-table-column>
    
        <el-table-column label="操作">
        <template slot-scope="scope">
            <el-button
            size="mini"
            @click="editRole(scope.row)" :disabled="editrolebuttonDisabled">编辑</el-button>
            <el-button
            size="mini"
            type="danger"
            @click="deleteRole(scope.row)" :disabled="removerolebuttonDisabled">删除</el-button>
            <el-button type="success" size="mini" @click="authempbButtionClick(scope.row)" :disabled="authempbuttonDisabled">分配用户</el-button>
            <el-button type="success" size="mini" @click="authbButtionClick(scope.row)" :disabled="authmenubuttonDisabled">设置权限</el-button>
        </template>
        </el-table-column>
        </el-table>

        <el-pagination background 
            layout="prev, pager, next, sizes, jumper, ->, total"
            :total="total" 
            :page-size="size"
            :page-sizes = "pagesizes"
            @next-click = "changePage"
            @prev-click = "changePage"
            @current-change = "changePage"
            @size-change = "changeSize"
        ></el-pagination>


        <el-dialog
            title="角色操作"
            :visible.sync="roledialogVisible"
            width="60%">
            
            <el-form :model="roleForm" :rules="rules" ref="roleForm" label-width="100px" class="demo-ruleForm">
                <el-form-item label="角色名称" prop="name">
                    <el-input type="text" placeholder="请输入角色名称" v-model="roleForm.roleName" maxlength="10" show-word-limit></el-input>
                </el-form-item>
                <el-form-item label="角色描述" prop="desc">
                    <el-input type="textarea" placeholder="请输入角色描述" v-model="roleForm.roleDesc" maxlength="200" show-word-limit></el-input>
                </el-form-item>
            </el-form>

            <span slot="footer" class="dialog-footer">
                <el-button @click="roledialogVisible = false">取 消</el-button>
                <el-button type="primary" @click="submitRoleForm">确 定</el-button>
            </span>
        </el-dialog>


        <el-dialog
            title="设置权限"
            :visible.sync="authdialogVisible"
            width="40%">
            <el-tree :data="authdata" 
                ref = "tree"
                :props="defaultProps"
                show-checkbox
                default-expand-all
                node-key="id"
                :default-checked-keys="authcheckkey"
                ></el-tree>
            <span slot="footer" class="dialog-footer">
                <el-button @click="authdialogVisible = false">取 消</el-button>
                <el-button type="primary" @click="submitRoleMenu">确 定</el-button>
            </span>
        </el-dialog>


        <el-dialog
            title="该角色已分配的用户："
            :visible.sync="authempdialogVisible"
            width="60%">
            <el-row style="height: 10px;"></el-row>
            <el-button type="success" size="medium" @click="authaddempbButtionClick">添加新用户</el-button>
            <el-row style="height: 10px;"></el-row>

            <template>
                <el-table
                    :data="authemptableData"
                    stripe
                    style="width: 100%">
                    <el-table-column prop="empNo" label="用户ID" width="180"></el-table-column>
                    <el-table-column prop="empName" label="用户名" width="180"></el-table-column>
                    <el-table-column prop="phone" label="手机号" width="180"></el-table-column>
                    <el-table-column label="操作">
                        <template slot-scope="scope">
                        <el-button
                        size="mini"
                        type="danger"
                        @click="deletroleemp(scope.row)">取消分配</el-button>   
                    </template>
                    </el-table-column>
                </el-table>
            </template>

            <el-pagination background 
                layout="prev, pager, next, sizes, jumper, ->, total"
                :total="alloctotal" 
                :page-size="allocsize"
                :page-sizes = "allocpagesizes"
                @next-click = "allocselectchangePage"
                @prev-click = "allocselectchangePage"
                @current-change = "allocselectchangePage"
                @size-change = "allocselectchangeSize"
            ></el-pagination>

            <span slot="footer" class="dialog-footer">
                <el-button @click="authempdialogVisible = false">关 闭</el-button>
            </span>
        </el-dialog>

        <el-dialog
            title="请选择要分配的新用户"
            :visible.sync="authnoaddedempdialogVisible"
            width="40%">
            
            <el-table :data="authnotaddedemptableData" border style="width: 100%"  @selection-change="noaddedempSelectionChange">
            <el-table-column type="selection" width="55"></el-table-column>
            <el-table-column prop="empNo" label="用户ID" width="100"></el-table-column>
            <el-table-column prop="empName" label="用户名" width="180"></el-table-column>
            <el-table-column prop="phone" label="手机号"></el-table-column>
            </el-table>

            <span slot="footer" class="dialog-footer">
                <el-button @click="authnoaddedempdialogVisible = false">取 消</el-button>
                <el-button type="primary" @click="suthsubmitnewemp">确 定</el-button>
            </span>

            <el-pagination background 
                layout="prev, pager, next, sizes, jumper, ->, total"
                :total="authtotal" 
                :page-size="authsize"
                :page-sizes = "authpagesizes"
                @next-click = "authselectchangePage"
                @prev-click = "authselectchangePage"
                @current-change = "authselectchangePage"
                @size-change = "authselectchangeSize"
            ></el-pagination>

        </el-dialog>
        

    </div>
</template>

<script>
export default {
    data(){
        return {
            tableData: [],
            authcheckkey:[8],
            queryParam: {
                roleName: "",
                roleDesc: "",
            },
            authdialogVisible: false,
            roledialogVisible: false,
            authempdialogVisible: false,
            authnoaddedempdialogVisible: false,
            total: 0,
            current:1,
            size: 5,
            pagesizes: [5, 10, 20],
            roleForm: {
                roleName: '',
                roleDesc: '',
                status: 0,
            },
            rules: {
                roleName: [
                    { required: true, message: '请输入角色名称', trigger: 'blur' },
                ],
                roleDesc: [
                    { required: true, message: '请输入角色描述', trigger: 'blur' },
                ]},
            authdata: [{
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
            authRoleMenu: {
                roleId: 1,
                menuIdList: []
            },
            editRoleId: 0,
            authemptableData: [],
            authnotaddedemptableData: [],
            selectauthNoaddedEmpList: [],
            authtotal: 0,
            authcurrent:1,
            authsize: 5,
            authpagesizes: [5, 10, 20],

            alloctotal: 0,
            alloccurrent:1,
            allocsize: 5,
            allocpagesizes: [5, 10, 20],

            addrolebuttonDisabled: true,
            editrolebuttonDisabled: true,
            removerolebuttonDisabled: true,
            findrolebuttonDisabled: true,
            authempbuttonDisabled: true,
            authmenubuttonDisabled: true,
        };
    },
    methods : {
        suthsubmitnewemp(){
            if(this.selectauthNoaddedEmpList.length > 0){
                this.http  
                .request({
                    url: "/auth/saveRoleEmp",
                    method: "POST",
                    data:{
                        roleId: this.editRoleId,
                        empIdList: this.selectauthNoaddedEmpList,
                    }
                })
                .then(({ data }) => {
                    // console.log(data);
                    if(data.code == 200){   
                        this.$message({
                            message: "为角色添加用户成功",
                            type: "success",
                        });
                        this.authnoaddedempdialogVisible = false;
                        this.refreshauthEmpByRoleId();
                    }
                })
            }else{
                this.authnoaddedempdialogVisible = false;
                this.$message({
                    message: "取消添加",
                    type: "info",
                });
            }
        },
        authselectchangeSize(size){
            this.authsize = size;
            this.refreshauthaddempbButtionClick();
        },
        authselectchangePage(current){
            // console.log(current);
            this.authcurrent = current;
            this.refreshauthaddempbButtionClick();
        },
        noaddedempSelectionChange(selection){
            let arr = [];
            for(var i = 0; i < selection.length; i++){
                arr.push(selection[i].empNo)
            }
            this.selectauthNoaddedEmpList = arr;
        },
        allocselectchangeSize(size){
            this.allocsize = size;
            this.refreshauthEmpByRoleId();
        },
        allocselectchangePage(current){
            // console.log(current);
            this.alloccurrent = current;
            this.refreshauthEmpByRoleId();
        },
        deletroleemp(row){
            // console.log(row)
            this.$confirm('确认取消分配?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(() => {
                this.http
                .request({
                    url: "/auth/deleteRoleEmp",
                    method: "POST",
                    data:{
                        empId: row.empNo,
                        roleId: this.editRoleId,
                    }
                }).then(({ data }) => {
                    if(data.code == 200){
                        this.$message({
                            message: "角色删除用户成功",
                            type: "success",
                        });
                        this.alloccurrent = 1;
                        this.refreshauthEmpByRoleId();
                    }
                })
            }).catch(() => {
                this.$message({
                    type: 'info',
                    message: '已取消操作'
                });          
            });
        },
        refreshauthaddempbButtionClick(){
            this.http
                .request({
                    url: "auth/getNotAddedEmpByRoleId?id=" + this.editRoleId,
                    method: "POST",
                    data: {
                        pageNum: this.authcurrent,
                        pageSize: this.authsize,
                    }
                }).then(({ data }) => {
                    // debugger;
                    // console.log(data)
                    this.authnotaddedemptableData = data.data.records;
                    this.authnoaddedempdialogVisible = true;
                    this.authcurrent = data.data.current;
                    this.authsize = data.data.size;
                    this.authtotal = data.data.total;
                })
        },
        authaddempbButtionClick(){
            this.authcurrent = 1;
            this.authsize = 5;
            this.http
                .request({
                    url: "auth/getNotAddedEmpByRoleId?id=" + this.editRoleId,
                    method: "POST",
                    data: {
                        pageNum: this.authcurrent,
                        pageSize: this.authsize,
                    }
                }).then(({ data }) => {
                    this.authnotaddedemptableData = data.data.records;
                    this.authnoaddedempdialogVisible = true;
                    this.authcurrent = data.data.current;
                    this.authsize = data.data.size;
                    this.authtotal = data.data.total;
                })
        },
        refreshauthEmpByRoleId(){
            this.http
                .request({
                    url: "auth/getempByRoleId?id=" + this.editRoleId,
                    method: "POST",
                    data: {
                        pageNum: this.alloccurrent,
                        pageSize: this.allocsize,
                    }
                }).then(({ data }) => {
                    this.authemptableData = data.data.records;
                    this.alloccurrent = data.data.current;
                    this.allocsize = data.data.size;
                    this.alloctotal = data.data.total;
                })
        },
        getempByRoleId(row){
            this.editRoleId = row.id;
            this.http
                .request({
                    url: "auth/getempByRoleId?id=" + row.id,
                    method: "POST",
                    data: {
                        pageNum: this.alloccurrent,
                        pageSize: this.allocsize,
                    }
                }).then(({ data }) => {
                    // console.log(data);
                    this.authemptableData = data.data.records;
                    this.alloccurrent = data.data.current;
                    this.allocsize = data.data.size;
                    this.alloctotal = data.data.total;
                })
        },
        authempbButtionClick(row){
            this.alloccurrent = 1,
            this.allocsize = 5,
            this.authempdialogVisible = true;
            this.getempByRoleId(row);
        },
        // -------------------------------------------------------------
        submitRoleMenu(){
            console.log(this.$refs.tree.getCheckedKeys());
            var checkedNodes = this.$refs.tree.getCheckedKeys();
            this.authRoleMenu.menuIdList = [];
            for(var i = 0; i < checkedNodes.length; i++){
                if(checkedNodes[i].type != 0){
                    this.authRoleMenu.menuIdList.push(checkedNodes[i]);
                }
            }
            this.http
                .request({
                    url: "auth/saveRoleMenu",
                    method: "POST",
                    data: this.authRoleMenu
                }).then(({ data }) => {
                    if(data.code == 200){
                        this.$message({
                            message: "角色设置权限成功",
                            type: "success",
                        });
                        this.authdialogVisible = false;
                    }
                })
        },
        getAuthByRoleId(row){
            this.authcheckkey = [];
            this.http
                .request({
                    url: "auth/getMenuListByRoleId/" + row.id,
                    method: "GET",
                })
                .then(({ data }) => {
                    // console.log(data);
                    this.authRoleMenu.roleId = row.id
                    this.authcheckkey = data.data;
                })
        },
        authbButtionClick(row){
            this.authdialogVisible = true,
            this.getMenuList();
            this.getAuthByRoleId(row)
        },
        getMenuList(){
            this.http
                .request({
                    url: "menu/tree",
                    method:"GET",
                }).then(({ data }) => {
                    // console.log(data)
                    this.authdata = data.data
                })
        },
        resetsearch(){
            this.queryParam = [];
            this.getRoleList()
        },
        searchrole(){
            this.getRoleList();
        },
        changeSize(size){
            this.size = size;
            this.refreshRoleList();
        },
        changePage(current){
            this.current = current;
            this.refreshRoleList();
        },
        refreshRoleList(){
            var app = this;
            this.http
                .request({
                    url:"role/page?pageNum=" + this.current + "&pageSize=" + this.size,
                    method:"POST",
                    data: {
                        roleName: this.queryParam.roleName,
                        roleDesc: this.queryParam.roleDesc,
                    }
                })
                .then(({ data }) => {
                    app.tableData = data.data.records;
                    app.current = data.data.current;
                    app.size = data.data.size;
                    app.total = data.data.total;
                }).catch((error) => {
                    console.log(error);
                });
        },
        getRoleList(){
            this.current = 1;
            this.size = 5;
            var app = this;
            this.http
                .request({
                    url:"role/page?pageNum=" + this.current + "&pageSize=" + this.size,
                    method:"POST",
                    data: {
                        roleName: this.queryParam.roleName,
                        roleDesc: this.queryParam.roleDesc,
                    }
                })
                .then(({ data }) => {
                    app.tableData = data.data.records;
                    app.current = data.data.current;
                    app.size = data.data.size;
                    app.total = data.data.total;
                }).catch((error) => {
                    console.log(error);
                });
        },
        addRole(){
            this.roledialogVisible = true;
        },
        editRole(row){
            // console.log(row)
            this.http.request({
                    url: "role/info/" + row.id,
                    method: "GET",
                })
                .then(({ data }) => {
                    if(data.code == 200){
                        this.roleForm = data.data
                        this.roledialogVisible = true
                    };
                });
        },
        deleteRole(row){
            this.$confirm('确认删除?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(() => {
                this.http({
                    url: "role/remove/" + row.id,
                    method: "DELETE",
                }).then(({data}) => {
                    if(data.code == 200){
                        this.$message({
                            message: "删除角色成功",
                            type: "success",
                        });
                        this.getRoleList();
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
        submitRoleForm(){
            var operation = this.roleForm.id ? "update" : "save";

            this.http
                .request({
                    url: "role/" + operation,
                    method: "POST",
                    data: this.roleForm,
                })
                .then(({ data }) => {
                    if(data.code == 200){
                        var msg = "";
                        if(operation == "update"){
                            msg = "修改";
                        }else{
                            msg = "添加";
                        }
                        this.$message({
                            message: msg + "角色成功",
                            type: "success",
                        });
                        this.roleForm =  {
                            roleName: '',
                            roleDesc: ''
                        };
                        this.roledialogVisible = false;
                        this.getRoleList();
                    }
                });
        },
        getAuthButton(){
            this.addempbuttonDisabled = true;
            this.addrolebuttonDisabled = true,
            this.editrolebuttonDisabled = true,
            this.removerolebuttonDisabled = true,
            this.findrolebuttonDisabled = true,
            this.authempbuttonDisabled = true,
            this.authmenubuttonDisabled = true,
            this.http
                .request({
                    url: "auth/getAuth",
                    method: "GET",
                }).then(({ data }) => {
                    console.log(data)
                    let lst = data.data
                    for(let i = 0; i < lst.length; i++){
                        // console.log(lst[i])
                        if(lst[i] == "http://localhost:8080/role/save")
                            this.addrolebuttonDisabled = false;
                        if(lst[i] == "http://localhost:8080/role/remove/*")
                            this.removerolebuttonDisabled = false;
                        if(lst[i] == "http://localhost:8080/role/update")
                            this.editrolebuttonDisabled = false;
                        if(lst[i] == "http://localhost:8080/role/page")
                            this.findrolebuttonDisabled = false;
                        if(lst[i] == "http://localhost:8080/auth/getempByRoleId")
                            this.authempbuttonDisabled = false;
                        if(lst[i] == "http://localhost:8080/auth/getMenuListByRoleId/*")
                            this.authmenubuttonDisabled = false;
                    }
                })
        },
    },
    mounted(){
        this.queryParam = [];
        this.getRoleList();
        this.getAuthButton();
    },
};
</script>

<style>

</style>