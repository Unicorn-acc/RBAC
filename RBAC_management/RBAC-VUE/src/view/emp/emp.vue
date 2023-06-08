<template>
    <div>
        
        <el-row :gutter="20">
            <el-col :span="2"><div class="grid-content bg-purple">用户名：</div></el-col>
            <el-col :span="6"><div class="grid-content bg-purple">
                <el-input v-model="queryParam.empName" placeholder="请输入内容"></el-input>
            </div></el-col>
            <el-col :span="2"><div class="grid-content bg-purple">部门：</div></el-col>
            <el-col :span="6"><div class="grid-content bg-purple">
                <el-select v-model="queryParam.deptNo" placeholder="请选择">
                    <el-option
                    v-for="item in departOptions"
                    :key="item.value"
                    :label="item.label"
                    :value="item.value">
                    </el-option>
                </el-select>
            </div></el-col>
        </el-row>

        <el-row :gutter="20">
            <el-col :span="2"><div class="grid-content bg-purple">职务：</div></el-col>
            <el-col :span="6"><div class="grid-content bg-purple">
                <el-input v-model="queryParam.job" placeholder="请输入内容"></el-input>
            </div></el-col>
            <el-col :span="2"><div class="grid-content bg-purple">手机号：</div></el-col>
            <el-col :span="6"><div class="grid-content bg-purple">
                <el-input v-model="queryParam.phone" placeholder="请输入内容"></el-input>
            </div></el-col>
            <el-col :span="2"><div class="grid-content bg-purple">
                <el-button type="primary" round @click="searchemp" :disabled = "findempbuttonDisabled">搜索</el-button>
            </div></el-col>
            <el-col :span="1"><div class="grid-content bg-purple">
                <el-button type="primary" round @click="resetsearch">重置</el-button>
            </div></el-col>
        </el-row>
        
        <el-row style="height: 10px;">
        </el-row>

        <el-row >
            <el-button type="primary" round @click="addEmp" :disabled="addempbuttonDisabled">添加用户</el-button>
        </el-row>
        <el-row style="height: 10px;">
        </el-row>

        <el-table :data="tableData" border style="width: 100%">
        <el-table-column prop="empNo" label="用户ID" sortable width="100"></el-table-column>
        <el-table-column prop="empName" label="用户名" width="150"></el-table-column>
        <el-table-column prop="deptNo" label="所属部门" width="80"></el-table-column>
        <el-table-column prop="job" label="职务" width="120"></el-table-column>
        <el-table-column prop="phone" label="手机号"></el-table-column>
    
        <el-table-column label="操作">
        <template slot-scope="scope">
            <el-button
            size="mini"
            @click="editEmp(scope.row)"
            :disabled = "editempbuttonDisabled">编辑</el-button>
            <el-button
            size="mini"
            type="danger"
            @click="deleteEmp(scope.row)"
            :disabled = "removeempbuttonDisabled">删除</el-button>
            <el-button 
            type="success" 
            size="mini" 
            @click="authemprole(scope.row)"
            :disabled = "authrolebuttonDisabled"
            >分配角色</el-button>
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
    title="用户操作"
    :visible.sync="empdialogVisible"
    width="60%">
    
    <el-form :model="empForm" :rules="rules" ref="empForm" label-width="100px" class="demo-ruleForm">
        <el-form-item label="用户名" prop="empName">
            <el-input v-model="empForm.empName"></el-input>
        </el-form-item>
        <el-form-item label="密码" prop="password">
            <el-input v-model="empForm.password"></el-input>
        </el-form-item>
        <el-form-item label="所属部门" prop="deptNo">
            <el-select v-model="empForm.deptNo" placeholder="请选择所属部门">
            <el-option label="部门1" value=1></el-option>
            <el-option label="部门2" value=2></el-option>
            <el-option label="部门3" value=3></el-option>
            <el-option label="部门4" value=4></el-option>
            <el-option label="部门5" value=5></el-option>
            </el-select>
        </el-form-item>
        <el-form-item label="职务" prop="job">
            <el-input v-model="empForm.job"></el-input>
        </el-form-item>
        <el-form-item label="手机号" prop="phone">
            <el-input v-model="empForm.phone"></el-input>
        </el-form-item>
        
    </el-form>

    <span slot="footer" class="dialog-footer">
        <el-button @click="empdialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="submitEmpForm">确 定</el-button>
    </span>
</el-dialog>


    <el-dialog
        title="该用户已分配的角色："
        :visible.sync="authemproledialogVisible"
        width="60%">
        
        <el-row style="height: 10px;"></el-row>
        <el-button type="success" size="medium" @click="authaddroleButtionClick">添加新角色</el-button>
        <el-row style="height: 10px;"></el-row>

        <template>
            <el-table
                :data="authroletableData"
                stripe
                style="width: 100%">
                <el-table-column prop="id" label="角色ID" width="80"></el-table-column>    
                <el-table-column prop="roleName" label="角色名" width="180"></el-table-column>
                <el-table-column prop="roleDesc" label="角色描述" style = "width: 30%"></el-table-column>
                <el-table-column label="操作">
                    <template slot-scope="scope">
                    <el-button
                    size="mini"
                    type="danger"
                    @click="deletemprole(scope.row)">取消分配</el-button>   
                </template>
                </el-table-column>
            </el-table>
        </template>

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

        <span slot="footer" class="dialog-footer">
            <el-button @click="authemproledialogVisible = false">关 闭</el-button>
        </span>
    </el-dialog>


    <el-dialog
        title="请选择要分配的新角色"
        :visible.sync="authnoaddedroledialogVisible"
        width="40%">
        
        <el-table :data="authnotaddedroletableData" border style="width: 100%"  @selection-change="noaddedroleSelectionChange">
        <el-table-column type="selection" width="55"></el-table-column>
        <el-table-column prop="id" label="角色ID" width="80"></el-table-column>    
        <el-table-column prop="roleName" label="角色名" width="180"></el-table-column>
        <el-table-column prop="roleDesc" label="角色描述" style = "width: 30%"></el-table-column>
        </el-table>

        <span slot="footer" class="dialog-footer">
            <el-button @click="authnoaddedroledialogVisible = false">取 消</el-button>
            <el-button type="primary" @click="authsubmitnewrole">确 定</el-button>
        </span>

        <el-pagination background 
            layout="prev, pager, next, sizes, jumper, ->, total"
            :total="authselectroletotal" 
            :page-size="authselectrolesize"
            :page-sizes = "authselectrolepagesizes"
            @next-click = "autselectrolechangePage"
            @prev-click = "autselectrolechangePage"
            @current-change = "autselectrolechangePage"
            @size-change = "autselectrolechangeSize"
        ></el-pagination>

    </el-dialog>
    

    </div>
    
</template>

<script>
export default {
    data(){
        return {
        tableData: [],
        empdialogVisible: false,
        total: 0,
        current:1,
        size: 5,
        pagesizes: [5, 10, 20],
        queryParam:{
            empName:"",
            deptNo: 0,
            job:"",
            phone:"",
        },
        departOptions: [{
          value: 0,
          label: '请选择（默认所有）'
        },{
          value: 1,
          label: '部门1'
        }, {
          value: 2,
          label: '部门2'
        }, {
          value: 3,
          label: '部门3'
        }, {
          value: 4,
          label: '部门4'
        }, {
          value: 5,
          label: '部门5'
        }],
        empForm: {
          empName: 'defaultname',
          password:"123456",
          deptNo: 1,
          job: 'defaultjob',
          phone: '12345678901',
        },
        rules: {
           empName: [
            { required: true, message: '请输入用户名', trigger: 'blur' }
          ],
          deptNo: [
            { required: true, message: '请选择部门', trigger: 'change' }
          ],
          job: [
            { required: true, message: '请输入职务', trigger: 'blur' }
          ],
          phone: [
            { required: true, message: '请输入手机号', trigger: 'blur' },
            { min: 1, max: 11, message: '长度在 1 到 11 个字符', trigger: 'blur' }
          ],
        },
        editempid: 1,
        authemproledialogVisible: false,
        authroletableData : [],
        authtotal: 0,
        authcurrent:1,
        authsize: 5,
        authpagesizes: [2, 5, 10, 20],

        authnoaddedroledialogVisible: false,
        authnotaddedroletableData: [],
        selectauthNoaddedRoleList: [],
        authselectroletotal: 0,
        authselectrolecurrent:1,
        authselectrolesize: 5,
        authselectrolepagesizes: [5, 10, 20],

        addempbuttonDisabled: true,
        editempbuttonDisabled: true,
        removeempbuttonDisabled: true,
        findempbuttonDisabled: true,
        authrolebuttonDisabled: true,
        };
    },
    methods: {
        authsubmitnewrole(){
            if(this.selectauthNoaddedRoleList.length > 0){
                this.http  
                .request({
                    url: "/auth/saveEmpRole",
                    method: "POST",
                    data:{
                        empId: this.editempid,
                        roleIdList: this.selectauthNoaddedRoleList,
                    }
                })
                .then(({ data }) => {
                    // console.log(data);
                    if(data.code == 200){   
                        this.$message({
                            message: "为用户添加角色成功",
                            type: "success",
                        });
                        this.authnoaddedroledialogVisible = false;
                        this.refreshauthemprole();
                    }
                })
            }else{
                this.authnoaddedroledialogVisible = false;
                this.$message({
                    message: "取消添加",
                    type: "info",
                });
            }
            
        },
        autselectrolechangeSize(size){
            this.authselectrolesize = size;
            this.refreshauthaddrole();
        },
        autselectrolechangePage(current){
            this.authselectrolecurrent = current;
            this.refreshauthaddrole();
        },
        noaddedroleSelectionChange(selection){
            let arr = [];
            for(var i = 0; i < selection.length; i++){
                arr.push(selection[i].id)
            }
            this.selectauthNoaddedRoleList = arr;
        },
        refreshauthaddrole(){
            this.http
                .request({
                    url: "auth/getNotAddedRoleByEmpId?id=" + this.editempid,
                    method: "POST",
                    data: {
                        pageNum: this.authselectrolecurrent,
                        pageSize: this.authselectrolesize,
                    }
                }).then(({ data }) => {
                    console.log(data)
                    this.authnotaddedroletableData = data.data.records;
                    this.authnoaddedroledialogVisible = true;
                    this.authselectrolecurrent = data.data.current;
                    this.authselectrolesize = data.data.size;
                    this.authselectroletotal = data.data.total;
                })
        },
        authaddroleButtionClick(){
            this.authselectrolecurrent = 1;
            this.authselectrolesize = 5;
            this.http
                .request({
                    url: "auth/getNotAddedRoleByEmpId?id=" + this.editempid,
                    method: "POST",
                    data: {
                        pageNum: this.authselectrolecurrent,
                        pageSize: this.authselectrolesize,
                    }
                }).then(({ data }) => {
                    console.log(data)
                    this.authnotaddedroletableData = data.data.records;
                    this.authnoaddedroledialogVisible = true;
                    this.authselectrolecurrent = data.data.current;
                    this.authselectrolesize = data.data.size;
                    this.authselectroletotal = data.data.total;
                })
            this.authnoaddedroledialogVisible = true;
        },
        deletemprole(row){
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
                        empId: this.editempid,
                        roleId: row.id,
                    }
                }).then(({ data }) => {
                    if(data.code == 200){
                        this.$message({
                            message: "为用户删除角色成功",
                            type: "success",
                        });
                        this.authcurrent = 1;
                        this.refreshauthemprole();
                    }
                })
            }).catch(() => {
                this.$message({
                    type: 'info',
                    message: '已取消操作'
                });          
            });
        },
        authselectchangeSize(size){
            this.authsize = size;
            this.refreshauthemprole();
        },
        authselectchangePage(current){
            // console.log(current);
            this.authcurrent = current;
            this.refreshauthemprole();
        },
        refreshauthemprole(){
            this.http
                .request({
                    url: "auth/getRoleByEmpId?id=" + this.editempid,
                    method: "POST",
                    data: {
                        pageNum: this.authcurrent,
                        pageSize: this.authsize,
                    }
                }).then(({ data }) => {
                    if(data.code == 200){
                        console.log(data)
                        this.authroletableData = data.data.records,
                        this.authcurrent = data.data.current;
                        this.authsize = data.data.size;
                        this.authtotal = data.data.total;
                    }
                })
        },
        authemprole(row){
            this.editempid = row.empNo;
            this.authcurrent = 1,
            this.authsize = 5,
            this.http
                .request({
                    url: "auth/getRoleByEmpId?id=" + row.empNo,
                    method: "POST",
                    data: {
                        pageNum: this.authcurrent,
                        pageSize: this.authsize,
                    }
                }).then(({ data }) => {
                    if(data.code == 200){
                        console.log(data)
                        this.authroletableData = data.data.records,
                        this.authcurrent = data.data.current;
                        this.authsize = data.data.size;
                        this.authtotal = data.data.total;
                    }
                })
            this.authemproledialogVisible = true
        },
        searchemp(){
            console.log(this.queryParam)
            this.getEmpList()
        },
        resetsearch(){
            this.queryParam = []
            this.getEmpList()
        },
        changeSize(size){
            this.size = size;
            this.refreshEmpList();
        },
        changePage(current){
            // console.log(current);
            this.current = current;
            this.refreshEmpList();
        },
        addEmp(){
            this.empForm = {
                empName: 'defaultname',
                password:"123456",
                deptNo: 1,
                job: 'defaultjob',
                phone: '12345678901',
            },
            this.empdialogVisible = true;
        },
        editEmp(row){
            //  编辑用户信息
            this.http({
                url: "emp/info/" + row.empNo,
                method: "GET",
            }).then(({data}) => {
                // console.log("data", data.data)
                this.empForm = data.data;
                this.empdialogVisible = true;
            })
        },
        deleteEmp(row){
            this.$confirm('确认删除?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(() => {
                // debugger;
                this.http({
                    url: "emp/remove/" + row.empNo,
                    method: "DELETE",
                }).then(({data}) => {
                    if(data.code == 200){
                        this.$message({
                            message: "删除用户成功",
                            type: "success",
                        });
                        this.selectEmpList = []
                        this.getEmpList();
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
        deleteEmpList(){
            this.$confirm('确认删除?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(() => {
                debugger;
                this.http({
                    url: "emp/remove",
                    method: "POST",
                    data: this.selectEmpList,
                }).then(({data}) => {
                    if(data.code == 200){
                        this.$message({
                            message: "删除用户成功",
                            type: "success",
                        });
                        this.selectEmpList = []
                        this.getEmpList();
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
        refreshEmpList(){
            var app = this;
            this.http
                .request({
                    url: "emp/page?pageNum=" + this.current + "&pageSize=" + this.size,
                    method: "POST",
                    data:{
                        empName: this.queryParam.empName,
                        deptNo: this.queryParam.deptNo,
                        job : this.queryParam.job,
                        phone: this.queryParam.phone,
                    }
                })
                .then(({ data }) => {
                    //console.log(data);
                    app.tableData = data.data.records;
                    app.current = data.data.current;
                    app.size = data.data.size;
                    app.total = data.data.total;
                })
                .catch((error) => {
                    // console.log(error);
                });
        },
        getEmpList(){
            this.current = 1;
            this.size = 5;
            var app = this;
            this.http
                .request({
                    url: "emp/page?pageNum=" + this.current + "&pageSize=" + this.size,
                    method: "POST",
                    data:{
                        empName: this.queryParam.empName,
                        deptNo: this.queryParam.deptNo,
                        job : this.queryParam.job,
                        phone: this.queryParam.phone,
                    }
                })
                .then(({ data }) => {
                    //console.log(data);
                    app.tableData = data.data.records;
                    app.current = data.data.current;
                    app.size = data.data.size;
                    app.total = data.data.total;
                })
                .catch((error) => {
                    // console.log(error);
                });
        },
        submitEmpForm(){
            // console.log(this.empForm);
            // 根据是否有empNo，判断一下是添加还是更新
            var operation = this.empForm.empNo ? "update" : "save";

            this.http({
                url:"emp/" + operation,
                method: "POST",
                data: this.empForm,
            }).then(({ data }) => {
                if(data.code == 200) {
                    var msg = "";
                    if(operation == "update"){
                        msg = "修改";
                    }else{
                        msg = "添加";
                    }
                    this.$message({
                        message: msg + "用户成功",
                        type: "success",
                    });
                    // 关闭对话框，刷新页面 
                    this.empForm = {
                        empName: 'defaultname',
                        password:"123456",
                        deptNo: 1,
                        job: 'defaultjob',
                        phone: '12345678901',
                        },
                    this.empdialogVisible = false;
                    this.getEmpList();
                } else {
                    // this.$message.error(data.msgr);
                }
            });
        },
        getAuthButton(){
            this.addempbuttonDisabled = true;

            this.http
                .request({
                    url: "auth/getAuth",
                    method: "GET",
                }).then(({ data }) => {
                    console.log(data)
                    let lst = data.data
                    for(let i = 0; i < lst.length; i++){
                        // console.log(lst[i])
                        if(lst[i] == "http://localhost:8080/emp/save")
                            this.addempbuttonDisabled = false;
                        if(lst[i] == "http://localhost:8080/emp/remove/*")
                            this.removeempbuttonDisabled = false;
                        if(lst[i] == "http://localhost:8080/emp/update")
                            this.editempbuttonDisabled = false;
                        if(lst[i] == "http://localhost:8080/emp/page")
                            this.findempbuttonDisabled = false;
                        if(lst[i] == "http://localhost:8080/auth/getRoleByEmpId")
                            this.authrolebuttonDisabled = false;
                    }
                })
        },
    },

    mounted(){
        this.queryParam = [];
        this.getEmpList();
        this.getAuthButton();
    },
};
</script>

<style>

</style>