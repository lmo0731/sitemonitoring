/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
var uriargs = {};
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
            //+ "." + padDigits(this.getMilliseconds(), 2);
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
    uriargs.max = uriargs.max ? uriargs.max : 20;
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
    params._first = uriargs.first;
    params._max = uriargs.max;
    if (params) {
        uri += '?' + jQuery.param(params);
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
        _name: 'Measure.findLastMeasuresOfSite',
        site: 'i' + uriargs.site,
        _callback: 'processMeasures'
    });
}

function processMeasures(json) {
    var html = "";
    html += "<hr/>"
    if (json.info == "OK") {
        html += ("<table id='result'><tr><th>Measure</th><th>Value</th><th>Received date</th><th>Control</th></tr>");
        for (var i = 0; i < json.result.length; i++) {
            var date = json.result[i][0];
            var measure = json.result[i][1];
            var device = json.result[i][2];
            html += "<tr>";
            html += "<td>" + measure.name + "</td>";
            html += "<td>";
            var value = null;
            if (measure.value) {
                value = measure.value;
            }
            if (measure.info) {
                value = measure.info;
            }
            html += value;
            html += "</td>";
            html += "<td>" + date.toLocaleString() + "</td>"
            html += "<td><a href='#' onclick=\"controlSite('set', '" + device.udid + "', '" + measure.name + "','" + value + "')\">SET</a></td>";
            html += "</tr>";
        }
        html += ("</table>");
    } else {
        html += (json.info);
    }
    $("#content").append(html);
}

function controlSite(type, device, measure, value) {
    if ($("#dialog").length == 0) {
        $("#content").append("<div id='dialog' title='" + type + "' style='display: none'></div>");
    }
    //$("#dialog").html('test');
    $("#dialog").html("<div id='loading'></div><div id='result'></div><table><tr><input id='key' value='" + measure + "'/></td></tr><tr><td><input id='value' value='" + value + "'/></td></tr><tr><td><input type='submit' value='Set' id='submit'/></td></tr></table>");
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
        uriargs.first = parseInt(uriargs.first) + 20;
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