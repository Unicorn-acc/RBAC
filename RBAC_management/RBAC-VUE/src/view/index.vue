<template>
    <div id="app">
      <el-container style="height: 695px; border: 1px solid #eee">
        <el-aside width="200px" style="background-color: rgb(238, 241, 246)">
          <el-menu :default-openeds="['1', '3']">
            <el-submenu index="1">
              <template slot="title">权限管理</template>
                <el-menu-item index="1-4-1">
                  <router-link to="/emp" :hidden="empbuttonDisabled">用户管理</router-link>
                </el-menu-item>
                <el-menu-item index="1-4-1">
                  <router-link to="/role" :hidden="rolebuttonDisabled">角色管理</router-link>
                </el-menu-item>
                <el-menu-item index="1-4-1">
                  <router-link to="/menu" :hidden="menubuttonDisabled">菜单管理</router-link>
                </el-menu-item>
            </el-submenu>
          </el-menu>
        </el-aside>
      <el-container>
  
      <el-header style="text-align: right; font-size: 12px">
        <el-dropdown  @command="handleCommand">
          <i class="el-icon-setting" style="margin-right: 15px"></i>
          <el-dropdown-menu slot="dropdown">
            <el-dropdown-item command="updateinfo">修改密码</el-dropdown-item>
            <el-dropdown-item command="logout">注销</el-dropdown-item>
          </el-dropdown-menu>
        </el-dropdown>
        <span v-text="username"></span>
      </el-header>
      <el-main>

        <router-view></router-view>
        
      </el-main>
    </el-container>
  </el-container>
  
    <el-dialog
      title="修改密码："
      :visible.sync="userdialogVisible"
      width="30%">

      <el-form :model="infoParam" label-width="100px"
                :rules="rules" ref="infoParam">
          <el-form-item label="用户名：" prop="username">
              <el-input style="width: 200px" type="text" v-model="username"
                        autocomplete="off" size="small" readonly=""></el-input>
          </el-form-item>
          <el-form-item label="旧密码：" prop="oripassword">
              <el-input style="width: 200px" type="text" v-model="infoParam.oripassword"
                        show-password autocomplete="off" size="small"></el-input>
          </el-form-item>
          <el-form-item label="新密码：" prop="newpassword">
              <el-input style="width: 200px" type="password" v-model="infoParam.newpassword"
                        show-password autocomplete="off" size="small"></el-input>
          </el-form-item>
      </el-form>

      <span slot="footer" class="dialog-footer">
        <el-button @click="userdialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="submitnewpassword">确 定</el-button>
      </span>
    </el-dialog>
  
    </div>
  </template>
  
  <script>
    export default {
      data() {
        return {
            userid: 1,
            username: "admin",
            userdialogVisible: false,
            infoParam:{
              oripassword: "",
              newpassword: "",
            },
            rules:{
              oripassword: [
                    { required: true, message: '请输入原密码', trigger: 'blur' },
                    { min: 6, max: 12, message: '密码长度在 6 到 12 个字符', trigger: 'blur' }
                ],
                newpassword: [
                    { required: true, message: '请输入新密码', trigger: 'blur' },
                    { min: 6, max: 12, message: '密码长度在 6 到 12 个字符', trigger: 'blur' }
                ]
            },

            empbuttonDisabled: true,
            rolebuttonDisabled: true,
            menubuttonDisabled: true,
        }
      },
      methods : {
        submitnewpassword(){
          let app = this
            this.$refs.infoParam.validate((valid) => {
                if (valid) { //valid成功为true，失败为false
                    this.http
                        .request({
                          url: "updatepassword",
                          method: "POST",
                          data: {
                            emp_id: this.userid,
                            oripassword: this.infoParam.oripassword,
                            newpassword: this.infoParam.newpassword,
                          }
                        }).then(({ data }) => {
                            if(data.code == 200){
                              this.$message({
                                  message: "密码修改成功！",
                                  type: "success",
                              });
                              this.userdialogVisible = false;
                            }
                        })
                } else {
                    this.$message({
                            message: "校验失败，请重新输入。",
                            type: "error",
                        });
                    return false;
                }
            });
        },
        handleCommand(command) {
          if(command == "updateinfo"){
            this.updateinfo();
          }else if(command == "logout"){
            this.logout();
          }
        },
        logout(){
            localStorage.removeItem("token");
            this.$router.push("/login");
        },
        updateinfo(){
          this.infoParam = {
              oripassword: "",
              newpassword: "",
          }
          if (typeof this.$refs.infoParam !== "undefined") {
            this.$refs.infoParam.resetFields();
          }
          this.userdialogVisible = true;

        },
        getLoginUser(){
            this.http
            .request({
                url:"userinfo",
                method: "GET",
            })
            .then(({ data }) => {
                console.log(data);
                if(data.code == 200){
                  this.username = data.data.userName;
                  this.userid = data.data.userId;
                }
            })
        },
        getAuthButton(){
            this.empbuttonDisabled = true,
            this.rolebuttonDisabled = true,
            this.menubuttonDisabled = true,
            this.http
                .request({
                    url: "auth/getAuth",
                    method: "GET",
                }).then(({ data }) => {
                    console.log(data)
                    let lst = data.data
                    for(let i = 0; i < lst.length; i++){
                        // console.log(lst[i])
                        if(lst[i] == "http://localhost:8080/emp/page")
                          this.empbuttonDisabled = false;
                        if(lst[i] == "http://localhost:8080/role/page")
                          this.rolebuttonDisabled = false;
                        if(lst[i] == "http://localhost:8080/menu/tree")
                          this.menubuttonDisabled = false;
                    }
                })
        },
    },
      
      mounted(){
        this.getAuthButton();
        this.getLoginUser();
    },
    };
    
  </script>
  
  <style>
    .el-header {
      background-color: #B3C0D1;
      color: #333;
      line-height: 60px;
    }
    
    .el-aside {
      color: #333;
    }
  </style>
  