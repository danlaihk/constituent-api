
window.onload = function(){
    var app = new Vue({
        el: '[data-renderer="constituent-list"]',
        data: {
            raw: '<td>Hello Vue!</td>',
            jdata: undefined
        },
        mounted () {
            var self = this;
            axios
                .get('/api/constituent/list')
                .then(function(res){
                    console.log(res.data);
                    self.jdata = res.data;
                })
                .catch(function (error) { // 请求失败处理
                    console.log(error);
                });
        }

    });

    console.log(app);
};
