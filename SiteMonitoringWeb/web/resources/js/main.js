/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
var uriargs = {};

var desc = {
    "em.apvo": ["Цахилгаан тоолуурын","A фазын хүчдэл",null, "вольт"],
    "em.bpvo": ["Цахилгаан тоолуурын","B фазын хүчдэл",null, "вольт"],
    "em.cpvo": ["Цахилгаан тоолуурын","C фазын хүчдэл",null, "вольт"],
    "em.apcu": ["Цахилгаан тоолуурын","A фазын гүйдэл",null, "ампер"],
    "em.bpcu": ["Цахилгаан тоолуурын","B фазын гүйдэл",null, "ампер"],
    "em.cpcu": ["Цахилгаан тоолуурын","C фазын гүйдэл",null, "ампер"],
    "em.appf": ["Цахилгаан тоолуурын","A чадлын коэффициент",null, ""],
    "em.bppf": ["Цахилгаан тоолуурын","B чадлын коэффициент",null, ""],
    "em.cppf": ["Цахилгаан тоолуурын","C чадлын коэффициент",null, ""],
    "em.aate": ["Цахилгаан тоолуурын","Active Total Energy",null,"кВт/цаг"],
    "em.aase": ["Цахилгаан тоолуурын","Active Sharp Energy",null,"кВт/цаг"],
    "em.aape": ["Цахилгаан тоолуурын","Active Peak Energy",null,"кВт/цаг"],
    "em.aaop": ["Цахилгаан тоолуурын","Active Off-Peak Energy",null,"кВт/цаг"],
    "em.aash": ["Цахилгаан тоолуурын","Active Shoulder Energy",null,null],
    "em.irte": ["Цахилгаан тоолуурын","Reactive total energy",null,null],
    "em.iret1": ["Цахилгаан тоолуурын","Reactive tariff 1",null,null],
    "em.iret2": ["Цахилгаан тоолуурын","Reactive tariff 2",null,null],
    "em.iret3": ["Цахилгаан тоолуурын","Reactive tariff 3",null,null],
    "em.iret4": ["Цахилгаан тоолуурын","Reactive tariff 4",null,null],
    "ge.temp": ["Цахилгааны генераторын","Моторын хөргүүрийн шингэны температур",null,"цельс"],
    "ge.pressure": ["Цахилгааны генераторын","Моторын тосын даралт",null,"кПа"],
    "ge.fuel": ["Цахилгааны генераторын","Түлшний хэмжээ",null,"%"],
    "ge.frequency": ["Цахилгааны генераторын","Гаралтын хувьсах хүчдэлийн давтамж",null,"Гц"],
    "ge.operation_mode": ["Цахилгааны генераторын","Ажиллагааны горим",{
        "0": "унтарсан",
        "1": "автомат горим",
        "2": "ассан"
    },null],
    "ge.status": ["Цахилгааны генераторын","Моторын төлөв",{
        "0": "Асааж байна 1",
        "1": "Асааж байна 2",
        "2": "Асааж байна 3",
        "3": "Ассан",
        "4": "Хөргөхөд бэлдэж байна",
        "5": "Хөргөөж байна",
        "6": "Унтрааж байна",
        "7": "Унтраасан 2"
    },null],
    "ge.engine rpm": ["Цахилгааны генераторын","Моторын эргэлт",null,"эргэлт/мин"],
    "ge.battery volt": ["Цахилгааны генераторын","Баттерейн хүчдэл",null,"Вольт"],
    "ge.cool down duration": ["Цахилгааны генераторын","Моторыг зогсооход үлдсэн хугацаа",null,"сек"],
    "da.bat1.1": ["Шулуутгагчийн","Battery 1-н хүчдэл",null,null],
    "da.bat1.2": ["Шулуутгагчийн","Battery 1-н гүйдэл",null,null],
    "da.bat1.3": ["Шулуутгагчийн","Battery 1-н температур",null,null],
    "da.bat2.1": ["Шулуутгагчийн","Battery 2-н хүчдэл",null,null],
    "da.bat2.2": ["Шулуутгагчийн","Battery 2-н гүйдэл",null,null],
    "da.bat2.3": ["Шулуутгагчийн","Battery 2-н температур",null,null],
    "da.smro": ["Шулуутгагчийн","Модулын хүчдэлийн гаралт",null,null],
    "da.smri.1": ["Шулуутгагчийн","Модул 1-н гаралтын гүйдэл",null,null],
    "da.smri.2": ["Шулуутгагчийн","Модул 2-н гаралтын гүйдэл",null,null],
    "da.smri.3": ["Шулуутгагчийн","Модул 3-н гаралтын гүйдэл",null,null],
    "da.smr1.1": ["Шулуутгагчийн","Модул 1-н төлөв",null,null],
    "da.smr1.2": ["Шулуутгагчийн","Модул 1-н аларм",null,null],
    "da.smr2.1": ["Шулуутгагчийн","Модул 2-н төлөв",null,null],
    "da.smr2.2": ["Шулуутгагчийн","Модул 2-н аларм",null,null],
    "da.smr3.1": ["Шулуутгагчийн","Модул 3-н төлөв",null,null],
    "da.smr3.2": ["Шулуутгагчийн","Модул 3-н аларм",null,null],
    "da.dcou.1": ["Шулуутгагчийн","Тогтмол хүчдэлийн гаралт",null,null],
    "da.dcou.1": ["Шулуутгагчийн","Гаралтын гүйдлыг утга",null,null],
    "da.alrm_info": ["Шулуутгагчийн","Дохиоллын текст",null,null],
    "da.alrm_date": ["Шулуутгагчийн","Дохиоллын огноо",null,null],
    "da.acin.1": ["Шулуутгагчийн","A фазын хүчдэл",null,null],
    "da.acin.2": ["Шулуутгагчийн","B фазын хүчдэл",null,null],
    "da.acin.3": ["Шулуутгагчийн","C фазын хүчдэл",null,null],
    "da.acin.a": ["Шулуутгагчийн","A фазын хүчдэл",null,null],
    "da.acin.b": ["Шулуутгагчийн","B фазын хүчдэл",null,null],
    "da.acin.c": ["Шулуутгагчийн","C фазын хүчдэл",null,null],
    "da.brf1": ["Шулуутгагчийн","LLVD1 пускателиудийн төлөв",null,null],
    "da.brf2": ["Шулуутгагчийн","LLVD2 пускателиудийн төлөв",null,null],
    "re.bat1.1": ["Шулуутгагчийн","Battery 1-н хүчдэл",null,null],
    "re.bat1.2": ["Шулуутгагчийн","Battery 1-н гүйдэл",null,null],
    "re.bat1.3": ["Шулуутгагчийн","Battery 1-н температур",null,null],
    "re.bat2.1": ["Шулуутгагчийн","Battery 2-н хүчдэл",null,null],
    "re.bat2.2": ["Шулуутгагчийн","Battery 2-н гүйдэл",null,null],
    "re.bat2.3": ["Шулуутгагчийн","Battery 2-н температур",null,null],
    "re.smro": ["Шулуутгагчийн","Модулын хүчдэлийн гаралт",null,null],
    "re.smri.1": ["Шулуутгагчийн","Модул 1-н гаралтын гүйдэл",null,null],
    "re.smri.2": ["Шулуутгагчийн","Модул 2-н гаралтын гүйдэл",null,null],
    "re.smri.3": ["Шулуутгагчийн","Модул 3-н гаралтын гүйдэл",null,null],
    "re.smr1.1": ["Шулуутгагчийн","Модул 1-н төлөв",null,null],
    "re.smr1.2": ["Шулуутгагчийн","Модул 1-н аларм",null,null],
    "re.smr2.1": ["Шулуутгагчийн","Модул 2-н төлөв",null,null],
    "re.smr2.2": ["Шулуутгагчийн","Модул 2-н аларм",null,null],
    "re.smr3.1": ["Шулуутгагчийн","Модул 3-н төлөв",null,null],
    "re.smr3.2": ["Шулуутгагчийн","Модул 3-н аларм",null,null],
    "re.dcou.1": ["Шулуутгагчийн","Тогтмол хүчдэлийн гаралт",null,null],
    "re.dcou.1": ["Шулуутгагчийн","Гаралтын гүйдлыг утга",null,null],
    "re.alrm_info": ["Шулуутгагчийн","Дохиоллын текст",null,null],
    "re.alrm_date": ["Шулуутгагчийн","Дохиоллын огноо",null,null],
    "re.acin.1": ["Шулуутгагчийн","A фазын хүчдэл",null,null],
    "re.acin.2": ["Шулуутгагчийн","B фазын хүчдэл",null,null],
    "re.acin.3": ["Шулуутгагчийн","C фазын хүчдэл",null,null],
    "re.acin.a": ["Шулуутгагчийн","A фазын хүчдэл",null,null],
    "re.acin.b": ["Шулуутгагчийн","B фазын хүчдэл",null,null],
    "re.acin.c": ["Шулуутгагчийн","C фазын хүчдэл",null,null],
    "re.brf1": ["Шулуутгагчийн","LLVD1 пускателиудийн төлөв",null,null],
    "re.brf2": ["Шулуутгагчийн","LLVD2 пускателиудийн төлөв",null,null],
    "ac.on_off": ["Эйркондиционерийн","Ажиллагааны төлөв",{
        "0":"унтарсан",
        "1":"ассан"
    },null],
    "ac.swing": ["Эйркондиционерийн","Үлээлтийн төлөв",{
        "0":"бүх чиглэлд",
        "1":"хөдөлгөөнгүй"
    },null],
    "ac.mode": ["Эйркондиционерийн","Температурын төлөв",{
        "1":"халуун",
        "2":"хүйтэн",
        "3":"сэнс",
        "4":"чиигшилтэй",
        "5":"автомат"
    },null],
    "ac.fan_speed": ["Эйркондиционерийн","Сэнсны хурд",{
        "1":"автомат",
        "2":"хурдан",
        "3":"дунд",
        "4":"удаан"
    },null],
    "ac.alarm": ["Эйркондиционерийн","Түгшүүр",null,null],
    "ac.temp": ["Эйркондиционерийн","Температур",null,"цельс"],
    "ac.room_temp": ["Эйркондиционерийн","Өрөөний температур",null,"цельс"],
    "ac.coil1_temp": ["Эйркондиционерийн","Ороомог 1-н температур",null,"цельс"],
    "ac.coil2_temp": ["Эйркондиционерийн","Ороомог 2-н температур",null,"цельс"],
    "ac.compr_temp": ["Эйркондиционерийн","compr-н температур",null,"цельс"],
    "ac.cooling_temp": ["Эйркондиционерийн","Хөргөх температур",null,"цельс"],
    "ac.heating_start": ["Эйркондиционерийн","Автоматаар халаах температур",null,"цельс"],
    "ac.heating_stop": ["Эйркондиционерийн","Автоматаар зогсоох температур",null,"цельс"],
    "ac.time": ["Эйркондиционерийн","Унтраах хугацаа",null,"минут"]
}

var set = {
    "ge.on_off": ["Генераторыг","унтраах асаах",{
        "0": "унтраах",
        "1": "автомат горим",
        "2": "асаах"
    },null],
    "ge.temp" : ["Генераторын","температур",null, null],
    "ac.on_off": ["Эйркондиционерийг","унтраах асаах",{
        "0":"утнраах",
        "1":"асаах"
    },null],
    "ac.temp": ["Эйркондиционерийн","температур",null,"цельс"],
    "ac.swing": ["Эйркондиционерийг чиглэл",{
        "0":"бүх чиглэлд",
        "1":"хөдөлгөөнгүй"
    },null],
    "ac.mode": ["Эйркондиционерийн","горим",{
        "1":"халуун",
        "2":"хүйтэн",
        "3":"сэнс",
        "4":"чийгшилттэй",
        "5":"автомат"
    },null],
    "ac.fan_speed": ["Эйркондиционерийн","Сэнсны хурд",{
        "1":"автомат",
        "2":"хурдан",
        "3":"дунд",
        "4":"удаан"
    },null],
    "ac.time": ["Эйркондиционерийн","Унтраах хугацаа",null,"минут"],
    "ac.cooling_temp": ["Эйркондиционерийн","Хөргөх температур",null,"цельс"],
    "ac.heating_start": ["Эйркондиционерийн","Автоматаар халаах температур",null,"цельс"],
    "ac.heating_stop": ["Эйркондиционерийн","Автоматаар зогсоох температур",null,"цельс"]
}

Date.prototype.toISOString = function() {
    var padDigits = function padDigits(number, digits) {
        return Array(Math.max(digits - String(number).length + 1, 0)).join(0) + number;
    };
    return this.getFullYear()
    + "-" + padDigits((this.getMonth() + 1), 2)
    + "-" + padDigits(this.getDate(), 2)
    + " "
    + padDigits(this.getHours(), 2)
    + ":" + padDigits(this.getMinutes(), 2)
    + ":" + padDigits(this.getSeconds(), 2)
    // + "." + padDigits(this.getMilliseconds(), 2);
    + "";
};
$(document).ready(function() {
    var uri = window.location.search.toString();
    var idxq = uri.indexOf('?');
    var idxh = uri.indexOf("#");
    var parms = "";
    if (idxh > 0) {
        parms = uri.substring(idxq + 1, idxh);
    } else {
        parms = uri.substring(idxq + 1);
    }
    parms = parms.split("&");
    var json = "";
    for (var i in parms) {
        var parm = parms[i];
        var vals = parm.split('=');

        if (vals.length > 0) {
            if (i != 0) {
                json += ", ";
            }
            json += "'" + vals[0] + "': '" + decodeURIComponent((vals.length > 1 ? vals[1] : "").replace(/\+/g, '%20')) + "'";
        }
    }
    json = "({" + json + "})";
    var args = eval(json);
    console.log(args);
    uriargs = args;
    uriargs.max = uriargs.max ? uriargs.max : 100;
    uriargs.first = uriargs.first ? uriargs.first : 0;
    if (args.site) {
        loadScript('report', {
            _name: 'Event.findLastEventOfDevicesOfSite',
            _callback: 'processDevices',
            site: 'i' + args.site
        });
    } else if (args.device) {
        if (args.begin && args.end) {
            loadScript('report', {
                _name: 'Event.findRangeByDevice',
                _callback: 'processEvents',
                device: 's' + args.device,
                from: 'dt' + args.begin,
                to: 'dt' + args.end
            });
        } else {
            loadScript('report', {
                _name: 'Event.findByDevice',
                _callback: 'processEvents',
                device: 's' + args.device,
                from: 'dt' + args.begin,
                to: 'to' + args.end
            });
        }
    } else {
        loadScript('report', {
            _name: 'Site.findAll',
            _callback: 'processSites'
        });
    }
//
});

function loadScript(uri, params) {
    if(!params) params = {};
    params._first = uriargs.first;
    params._max = uriargs.max;
    if (params) {
        if (uri.indexOf('?') > 0) {
            uri += '&' + jQuery.param(params);
        } else {
            uri += '?' + jQuery.param(params);
        }
    }
    console.log(uri);
    $("#script").html("<script type='text/javascript' src='" + uri + "'></script>");
}

function processSites(json) {
    var html = "<h3>SITES</h3>";
    if (json.info == "OK") {
        console.log(json);
        html += ("<table id='result'><tr><th>Site</th><th>Info</th></tr>");
        for (var i = 0; i < json.result.length; i++) {
            var site = json.result[i];
            html += ("<tr>");
            html += ("<td><a href='?site=" + site.id + "'>" + site.name + "</a></td>");
            html += ("<td>" + site.info + "</td>");
            html += ("</tr>");
        }
        html += ("</table>");
    } else {
        html += (json.info);
    }
    $("#content").html(html);
}

function processDevices(json) {
    var html = "";
    if (json.info == "OK") {
        html += ("<table id='result'><tr><th>Device</th><th>Info</th><th>Last active date</th></tr>");
        for (var i = 0; i < json.result.length; i++) {
            if ($("#dialog").length == 0) {
                $("#content").append("<div id='dialog' title='Control' style='display: none'></div>");
            }
            var device = json.result[i][0];
            var date = json.result[i][1];
            html += ("<tr>");
            html += ("<td><a href='?device=" + device.udid + "'>" + device.udid + "</a></td>");
            html += ("<td>" + device.info + "</td>");
            html += ("<td>" + date.toLocaleString() + "</td>");
            html += ("</tr>");
        }
        html += ("</table>");
    } else {
        html += (json.info);
    }
    $("#content").html(html);
    loadScript('report', {
        _name: 'Measure.findLastMeasureIdsOfSite',
        site: 'i' + uriargs.site,
        _callback: 'processMeasures'
    });
}

function processMeasures(json) {
    var html = "";
    $("#content").append("<hr/>");
    if (json.info == "OK") {
        var url = "_name=Measure.findByIds&id=&id=";
        for (var i = 0; i < json.result.length; i++) {
            url += '&id=i' + json.result[i];
            json.result[i] = 'i' + json.result[i];
        }
        //if (false)
        $("#content").append("<div id='measures'></div>");
        loadScript('report?_callback=processLastMeasures&' + url);
    } else {
        $("#content").append(json.info);
    }
    $("#content").append(html);
}

function processLastMeasures(json) {
    var html = "";
    console.log(json);
    if (json.info == 'OK') {
        html += ("<table id='result'><tr><th>Measure</th><th>Value</th><th>Received date</th><th>Control</th></tr>");
        for (var i = 0; i < json.result.length; i++) {
            var date = json.result[i][1];
            var measure = json.result[i][0];
            var device = json.result[i][2];
            var key = measure.name.toLowerCase();
            while(key.indexOf('.') < key.lastIndexOf('.') && !desc[key]){
                key = (key.substring(0, key.lastIndexOf('.')));
            }
            var keyStr = $.trim(key);
            if(desc[key]){
                keyStr = desc[key][0]+" "+desc[key][1];
            }
            html += "<tr>";
            html += "<td>" + keyStr + " (" + measure.name + ")</td>";
            html += "<td>";
            var value = "";
            value = measure.info;
            var valueStr = measure.info;
            if(desc[key]){
                if(desc[key][2]){
                    if(desc[key][2][measure.info]){
                        valueStr = desc[key][2][measure.info];
                    }
                }
                if(desc[key][3]){
                    valueStr += " " + desc[key][3];
                }
                valueStr = $.trim(valueStr);
            }
            html += valueStr;
            html += "</td>";
            html += "<td>" + date.toLocaleString() + "</td>"
            html += "<td>";
            if(set[key]){
                html += "<a href='#' onclick=\"controlSite('set', '" + device + "', '" + measure.name + "','" + value + "')\">SET</a>";
            }
            html += "</td>";
            html += "</tr>";
        }
        html += ("</table>");
        $("#content #measures").html(html);
    } else {
        $("#content #measures").html(json.info);
    }
}

function controlSite(type, device, measure, value) {
    if ($("#dialog").length == 0) {
        $("#content").append("<div id='dialog' title='" + type + "' style='display: none'></div>");
    }
    var key = measure;
    while(key.indexOf('.') < key.lastIndexOf('.') && !desc[key]){
        key = (key.substring(0, key.lastIndexOf('.')));
    }
    var html = "<div id='loading'></div><div id='result'></div><table><tr><input id='key' value='" + measure + "'/></td></tr>";
    html += "<tr><td><input id='value' value='" + value + "'/></td></tr>";
    html += "<tr><td><input type='submit' value='Set' id='submit'/></td></tr>";
    if(set[key] && set[key][2] && set[key][2][value]){
        html += "<tr><td><div>";
        for(var i in set[key][2]){
            html += "" + i + ": " + set[key][2][i] + "<br/>";
        }
        html += "</div></td></tr>"
    } else {
        html += "<tr><td><div>"+(desc[key][3]?desc[key][3]:"")+"</div></td></tr>"
    }
    html += "</table>";
    $("#dialog").html(html);
    $("#dialog #submit").click(function() {
        var key = ($('#dialog #key').val());
        var val = ($('#dialog #value').val());
        var url = 'control?_device=' + device + "&_name=" + type + "&" + key + "=s" + val;
        console.log(url);
        $.ajax({
            url: url,
            type: 'GET',
            beforeSend: function(data) {
                $("#dialog #result").html('');
                $("#dialog #loading").html("LOADING");
            },
            success: function(data) {
                console.log(data);
                if (data.info == 'OK') {
                    $("#dialog #result").html(data.result.additional);
                } else {
                    $("#dialog #result").html(data.info);
                }
            }
        }).fail(function(data) {
            $("#dialog #result").html("AJAX ERROR");
            console.log(data);
        }).always(function(data) {
            $("#dialog #loading").html('');
        });
    });
    $("#dialog").dialog();
}

function processEvents(json) {
    var now = new Date();
    var html = "<form method='get'>"
    + "<input name='device' value='" + uriargs.device + "' style='display: none'/>"
    + "<table id='search'><tr><td>From</td><td><input name='begin' value='" + (uriargs.begin ? uriargs.begin : (new Date(now.getTime() - 3600 * 24 * 1000).toISOString())) + "'/></td></tr>"
    + "<tr><td>To</td><td><input name='end' value='" + (uriargs.end ? uriargs.end : (new Date(now.getTime()).toISOString())) + "'/></td></tr>"
    + "<tr><input type='submit' value='Search'/><tr><table></form>";
    if (json.info == "OK") {
        html += ("<table id='result'><tr><th>Event</th><th>Info</th><th></th></tr>");
        console.log(json);
        for (var i = 0; i < json.result.length; i++) {
            var event = json.result[i];
            html += "<tr>";
            html += "<td>" + event.info + "</td>";
            html += "<td>" + event.measuredate.toLocaleString() + "</td>";
            html += "<td><a href='#' onclick='showEvent(" + event.id + ")'>Show details</a></td>";
            html += "</tr>";
        }
        html += ("</table>");
        uriargs.first = parseInt(uriargs.first) + parseInt(uriargs.max);
        var url = window.location.pathname + "?" + jQuery.param(uriargs);
        html += "<a href='" + url + "'>next</a>";
    } else {
        html += (json.info);
    }
    $("#content").html(html);

}

function showEvent(i) {
    $.ajax({
        url: 'report?_name=Measure.findByEvent&event=i' + i,
        type: 'GET'
    }).done(function(data) {
        console.log(data);
        if ($("#dialog").length == 0) {
            $("#content").append("<div id='dialog' title='Details' style='display: none'></div>");
        }
        var html = "";
        if (data.info == "OK") {
            html += "<table id='result'><tr><th>Name</th><th>Value</th></tr>";
            for (var i = 0; i < data.result.length; i++) {
                html += "<tr>";
                var measure = data.result[i];
                html += "<td>" + measure.name + "</td>";
                html += "<td>";
                if (measure.value) {
                    html += measure.value;
                }
                if (measure.info) {
                    html += measure.info;
                }
                html += "</td>";
                html += "</tr>";
            }
            html += "</table>";
        } else {
            html = data.info;
        }
        $("#dialog").html(html);
        $("#dialog").dialog();
    });
}

function recurse(json) {
    var htmlRetStr = "<ul class='recurseObj' >";
    for (var key in json) {
        if (json[key]) {
            if (typeof(json[key]) == 'object') {
                if (json[key]) {
                    htmlRetStr += "<li class='keyObj' ><strong>" + key + ":</strong>";
                    htmlRetStr += recurse(json[key]);
                    htmlRetStr += "</li>";
                }
            } else {
                htmlRetStr += "<li class='keyStr' ><strong>" + key + ': </strong>"';
                htmlRetStr += json[key];
                htmlRetStr += "</li>";
            }
        }
    }
    htmlRetStr += '</ul >';
    return htmlRetStr;
}
