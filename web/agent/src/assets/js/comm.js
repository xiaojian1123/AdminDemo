Date.prototype.format = function (__formatStr) {
    var show_day = ['星期日', '星期一', '星期二', '星期三', '星期四', '星期五', '星期六'];
    var o = {
        'M+': this.getMonth() + 1,
        'd+': this.getDate(),
        'h+': this.getHours(),
        'm+': this.getMinutes(),
        's+': this.getSeconds(),
        'q+': Math.floor((this.getMonth() + 3) / 3),
        'S': this.getMilliseconds(),
        'w': show_day[this.getDay()]
    };
    if (/(y+)/.test(__formatStr)) __formatStr = __formatStr.replace(RegExp.$1, (this.getFullYear() + '').substr(4 - RegExp.$1.length));
    for (var k in o)
        if (new RegExp('(' + k + ')').test(__formatStr)) __formatStr = __formatStr.replace(RegExp.$1, (RegExp.$1.length === 1) ? (o[k]) : (('00' + o[k]).substr(('' + o[k]).length)));
    return __formatStr;
};
