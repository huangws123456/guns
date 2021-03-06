/**
 * 角色详情对话框
 */
var DeptInfoDlg = {
    data: {
        pid: "",
        pName: ""
    }
};


layui.use(['layer', 'form', 'admin', 'ax'], function () {
    var $ = layui.jquery;
    var $ax = layui.ax;
    var form = layui.form;
    var admin = layui.admin;
    var layer = layui.layer;
    // 表单提交事件
    form.on('submit(formDemo)', function (data) {
        var formData = data.field;
        console.log(formData)
        if (formData.cmid == "101" && formData.schemeUrl == "" && formData.userId == "" && formData.userName == "") {
            Feng.success("分享链接、快手号、快手昵称至少输入一个条件！");
            return;
        } else if (formData.cmid == "102" && formData.comment == "") {
            Feng.success("请输入评论规则！");
            return;
        } else if (formData.cmid == "104" && formData.approveNum == "") {
            Feng.success("请输入点赞次数！");
            return;
        }
        var ajax = new $ax(Feng.ctxPath + "/KSconfig/submit", function (data) {
            Feng.success("提交成功！");
            send(data.data.cmid,'', data.data);
        }, function (data) {
            Feng.error("提交失败！" + data.responseJSON.message)
        });
        ajax.set(data.field);
        ajax.start();
    });
    $("#dictDetails").html('');
    var ajax = new $ax(Feng.ctxPath + "/dict/listDictsByCode", function (data) {

        for (var i = 0; i < data.data.length; i++) {
            var name = data.data[i].name;
            var code = data.data[i].code;
            $("#dictDetails").append('<option value="' + code + '">' + name + '</option>');
        }
        form.render();

    }, function (data) {
    });
    ajax.set("dictTypeCode", "CMID");
    ajax.start();

});