var keyStr = "ABCDEFGHIJKLMNOP" + "QRSTUVWXYZabcdef" + "ghijklmnopqrstuv"
    + "wxyz0123456789+/" + "=";
function encode(password) {
    var output = "";
    var chr1, chr2, chr3 = "";
    var enc1, enc2, enc3, enc4 = "";
    var i = 0;
    do {
        chr1 = password.charCodeAt(i++);
        chr2 = password.charCodeAt(i++);
        chr3 = password.charCodeAt(i++);
        enc1 = chr1 >> 2;
        enc2 = ((chr1 & 3) << 4) | (chr2 >> 4);
        enc3 = ((chr2 & 15) << 2) | (chr3 >> 6);
        enc4 = chr3 & 63;
        if (isNaN(chr2)) {
            enc3 = enc4 = 64;
        } else if (isNaN(chr3)) {
            enc4 = 64;
        }
        output = output + keyStr.charAt(enc1) + keyStr.charAt(enc2)
            + keyStr.charAt(enc3) + keyStr.charAt(enc4);
        chr1 = chr2 = chr3 = "";
        enc1 = enc2 = enc3 = enc4 = "";
    } while (i < password.length);
    return output;
}
//校验登录
function check() {
    if(checkName()&&checkPassword()){
        var password = document.getElementById("password").value;
        document.getElementById("md5_password").value = this.encode(password);
        document.getElementById("form").submit();
    }else {
        alert("登录失败");
    }
}
//输入框鼠标选中改变边框颜色
function changeStyle(s1,s2) {
    document.getElementById(s1).style.border = "1px solid #FE7300";
    document.getElementById(s1).style.outline = "none";
    document.getElementById(s2).style.border = "1px solid #FE7300";
}
//校验用户名
function checkName() {
    var basic = document.getElementById("basic-addon1");
    var username = document.getElementById("username");
    var checkName = document.getElementById("checkName");
    checkName.innerHTML="";
    var name1 = username.value;
    checkName.style.color = "red";
    if (name1 == "") {
        checkName.innerHTML = "用户不能为空";
        basic.style.border = "1px solid red";
        username.style.border = "1px solid red";
        return false;
    }
    if (name1.length < 4 || name1.length > 16) {
        checkName.innerHTML = "长度为4-12位";
        basic.style.border = "1px solid red";
        username.style.border = "1px solid red";
        return false;
    }
    basic.style.border = "1px solid #50df90";
    username.style.border = "1px solid #50df90";
    return true;
}
//校验密码
function checkPassword(){
    var basic = document.getElementById("basic-addon2");
    var password = document.getElementById("password");
    var checkPassword = document.getElementById("checkPassword");
    checkPassword.innerHTML="";
    var pw = password.value;
    checkPassword.style.color = "red";
    if (pw == "") {
        checkPassword.innerHTML = "密码不能为空";
        basic.style.border = "1px solid red";
        password.style.border = "1px solid red";
        return false;
    }
    if (pw.length < 6 || pw.length > 12) {
        checkPassword.innerHTML = "长度为6-12位";
        basic.style.border = "1px solid red";
        password.style.border = "1px solid red";
        return false;
    }
    basic.style.border = "1px solid #50df90";
    password.style.border = "1px solid #50df90";
    return true;
}
function changeStyleBlur(s1,s2){
    document.getElementById(s1).style.border = "1px solid #50df90";
    document.getElementById(s2).style.border = "1px solid #50df90";
}
function thirdLogin() {
    alert("登录")
}
function changeImg() {
    var imgSrc = document.getElementById("imgObj");
    var timestamp = (new Date()).valueOf();
    imgSrc.setAttribute("src",'public/captcha?code=' + timestamp);
}