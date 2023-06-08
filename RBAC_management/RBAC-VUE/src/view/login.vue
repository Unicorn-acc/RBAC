<template>
  <div>
    <template>
    <div class="loginbBody">
        <div class="loginDiv">
            <div class="login-content">
                <h1 class="login-title">用户登录</h1>
                <el-form :model="loginForm" label-width="100px"
                         :rules="rules" ref="loginForm">
                    <el-form-item label="用户名" prop="name">
                        <el-input style="width: 200px" type="text" v-model="loginForm.name"
                                  autocomplete="off" size="small"></el-input>
                    </el-form-item>
                    <el-form-item label="密码" prop="password">
                        <el-input style="width: 200px" type="password" v-model="loginForm.password"
                                  show-password autocomplete="off" size="small"></el-input>
                    </el-form-item>
                    <el-form-item>
                        <el-button type="primary" @click="confirm">登 录</el-button>
                    </el-form-item>
                </el-form>
            </div>
        </div>
    </div>
</template>
  </div>
</template>

<script>
export default {
    data(){
        return{
            loginForm:{
                name:'',
                password:''
            },
            rules:{
                name: [
                    { required: true, message: '请输入用户名', trigger: 'blur' },
                    { min: 3, max: 12, message: '用户名长度在 3 到 12 个字符', trigger: 'blur' }
                ],
                password: [
                    { required: true, message: '请输密码', trigger: 'blur' },
                    { min: 6, max: 12, message: '密码长度在 6 到 12 个字符', trigger: 'blur' }
                ]
            }
        }
    },
    methods:{
        confirm(){
            let app = this
            this.$refs.loginForm.validate((valid) => {
                if (valid) { //valid成功为true，失败为false
                    // console.log(this.loginForm)
                    this.http
                        .request({
                            url: "/login",
                            method: "POST",
                            data: this.loginForm
                        }).then(({ data }) => {
                            if(data.code == 200){
                                localStorage.setItem("token", data.data);
                                this.$router.push("/index");
                            }
                        });
                } else {
                    console.log('校验失败');
                    return false;
                }
            });
        },
    }
}
</script>

<style scoped >
    .loginbBody {
        width: 100%;
        height: 100%;
        background-color: #B3C0D1;
    }
    .loginDiv {
        position: absolute;
        top: 50%;
        left: 50%;
        margin-top: -200px;
        margin-left: -250px;
        width: 450px;
        height: 330px;
        background: #fff;
        border-radius: 5%;

    }
    .login-title {
        margin: 20px 0;
        text-align: center;
    }
    .login-content {
        width: 400px;
        height: 250px;
        position: absolute;
        top: 25px;
        left: 25px;
    }
</style>