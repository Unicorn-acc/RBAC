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
                <el-button type="primary" round @click="searchemp">搜索</el-button>
            </div></el-col>
            <el-col :span="1"><div class="grid-content bg-purple">
                <el-button type="primary" round @click="resetsearch">重置</el-button>
            </div></el-col>
        </el-row>
        
        <el-row style="height: 10px;">
        </el-row>

        <el-row >
            <el-button type="primary" round @click="addEmp">添加用户</el-button>
            <el-button type="danger" round @click="deleteEmpList">删除用户</el-button>
        </el-row>
        <el-table :data="tableData" border style="width: 100%" @selection-change="empSelectionChange">
        <el-table-column type="selection" width="55"></el-table-column>
        <el-table-column prop="empNo" label="用户ID" width="100"></el-table-column>
        <el-table-column prop="empName" label="用户名" width="180"></el-table-column>
        <el-table-column prop="deptNo" label="所属部门"></el-table-column>
        <el-table-column prop="job" label="职务"></el-table-column>
        <el-table-column prop="phone" label="手机号"></el-table-column>
    
        <el-table-column label="操作">
        <template slot-scope="scope">
            <el-button
            size="mini"
            @click="editEmp(scope.row)">编辑</el-button>
            <el-button
            size="mini"
            type="danger"
            @click="deleteEmp(scope.row)">删除</el-button>
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
    title="添加用户"
    :visible.sync="empdialogVisible"
    width="60%">
    
    <el-form :model="empForm" :rules="rules" ref="empForm" label-width="100px" class="demo-ruleForm">
        <el-form-item label="用户名" prop="empName">
            <el-input v-model="empForm.empName"></el-input>
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

    </div>
    
</template>

<script>
export default {
    data(){
        return {
        tableData: [],
        empdialogVisible: false,
        selectEmpList: [],
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
        };
    },
    methods: {
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
            this.getEmpList();
        },
        changePage(current){
            // console.log(current);
            this.current = current;
            this.getEmpList();
        },
        addEmp(){
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
                debugger;
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
        empSelectionChange(selection){
            // console.log(selection);
            let arr = [];
            for(var i = 0; i < selection.length; i++){
                arr.push(selection[i].empNo)
            }
            this.selectEmpList = arr;
        },
        getEmpList(){
            var app = this;
            this.http
                .request({
                    url: "emp/page",
                    method: "POST",
                    data:{
                        current: this.current,
                        size: this.size,
                        empName: this.queryParam.empName,
                        deptNo: this.queryParam.deptNo,
                        job : this.queryParam.job,
                        phone: this.queryParam.phone
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
                    this.$message({
                        message: "添加新用户成功",
                        type: "success",
                    });
                    // 关闭对话框，刷新页面 
                    this.empdialogVisible = false;
                    this.getEmpList();
                } else {
                    // this.$message.error(data.msgr);
                }
            });
        }
    },

    mounted(){
        this.queryParam = [];
        this.getEmpList();
    },
};
</script>

<style>

</style>