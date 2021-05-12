<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>linkTrack</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <link rel="stylesheet" href="/layui/css/layui.css" media="all">
    <link rel="stylesheet" href="/layui/css/global.css" media="all">
    <style>
        .site-doc-icon li {
            width: 222px;
        }

        .site-doc-icon li .layui-anim {
            width: 80px;
            height: 80px;
            line-height: 80px;
            margin: 0 auto 10px;
            text-align: center;
            background-color: #009688;
            cursor: pointer;
            color: #fff;
            border-radius: 50%;
        }

        .layui-top-box {
            padding: 40px 20px 20px 20px;
            color: #fff
        }

        .panel {
            margin-bottom: 17px;
            background-color: #fff;
            border: 1px solid transparent;
            border-radius: 3px;
            -webkit-box-shadow: 0 1px 1px rgba(0, 0, 0, .05);
            box-shadow: 0 1px 1px rgba(0, 0, 0, .05)
        }

        .panel-body {
            padding: 15px
        }

        .panel-title {
            margin-top: 0;
            margin-bottom: 0;
            font-size: 14px;
            color: inherit
        }

        .label {
            display: inline;
            padding: .2em .6em .3em;
            font-size: 75%;
            font-weight: 700;
            line-height: 1;
            color: #fff;
            text-align: center;
            white-space: nowrap;
            vertical-align: baseline;
            border-radius: .25em;
            margin-top: .3em;
        }

        .layui-red {
            color: red
        }

        .main_btn > p {
            height: 40px;
        }
    </style>
</head>
<body>
<fieldset class="layui-elem-field layui-field-title" style="margin-top: 50px;">
    <legend>LinkTrack调用链路追踪</legend>
</fieldset>

<div class="layui-tab layui-tab-brief" lay-filter="docDemoTabBrief">
    <ul class="layui-tab-title">
        <li class="layui-this">接口总览</li>
        <li>接口详情</li>
    </ul>
    <div class="layui-tab-content" style="height: 100px;">
        <div class="layui-tab-item layui-show">
            <fieldset class="layui-elem-field layui-field-title" style="margin-top: 30px;">
                <legend>接口统计</legend>
            </fieldset>

            <div class="layuimini-main layui-top-box">
                <div class="layui-row layui-col-space10">

                    <div class="layui-col-md3">
                        <div class="col-xs-6 col-md-3">
                            <div class="panel layui-bg-cyan">
                                <div class="panel-body">
                                    <div class="panel-title">
                                        <span class="label pull-right layui-bg-blue">接口总数</span>
                                    </div>
                                    <div class="panel-content">
                                        <h1 class="no-margins" >${system.totalNum}</h1>
                                        <small>当前接口总记录数</small>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>

                    <div class="layui-col-md3">
                        <div class="col-xs-6 col-md-3">
                            <div class="panel layui-bg-red">
                                <div class="panel-body">
                                    <div class="panel-title">
                                        <span class="label pull-right layui-bg-cyan">延迟响应</span>
                                    </div>
                                    <div class="panel-content">
                                        <h1 class="no-margins" >${system.delayNum}</h1>
                                        <small>当前延迟接口总数</small>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>

                    <div class="layui-col-md3">
                        <div class="col-xs-6 col-md-3">
                            <div class="panel layui-bg-green">
                                <div class="panel-body">
                                    <div class="panel-title">
                                        <span class="label pull-right layui-bg-orange">正常响应</span>
                                    </div>
                                    <div class="panel-content">
                                        <h1 class="no-margins" >${system.normalNum}</h1>
                                        <small>当前正常响应接口总数</small>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <fieldset class="layui-elem-field layui-field-title" style="margin-top: 30px;">
                <legend>响应统计</legend>
            </fieldset>
            <div class="layuimini-main layui-top-box">
                <div class="layui-row layui-col-space10">

                    <div class="layui-col-md3">
                        <div class="col-xs-6 col-md-3">
                            <div class="panel <#if system.avgRunTime gt config.timeThreshold >layui-bg-red<#else>layui-bg-green</#if>">
                                <div class="panel-body">
                                    <div class="panel-title">
                                        <span class="label pull-right layui-bg-blue">平均响应时长</span>
                                    </div>
                                    <div class="panel-content">
                                        <h1 class="no-margins">${system.avgRunTime}</h1>
                                        <small>平均响应（ms）</small>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>

                    <div class="layui-col-md3">
                        <div class="col-xs-6 col-md-3">
                            <div class="panel <#if system.maxRunTime gt config.timeThreshold >layui-bg-red<#else>layui-bg-green</#if>">
                                <div class="panel-body">
                                    <div class="panel-title">
                                        <span class="label pull-right layui-bg-blue">最大响应时长</span>
                                    </div>
                                    <div class="panel-content">
                                        <h1 class="no-margins">${system.maxRunTime}</h1>
                                        <small>最大响应（ms）</small>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>

                    <div class="layui-col-md3">
                        <div class="col-xs-6 col-md-3">
                            <div class="panel <#if system.minRunTime gt config.timeThreshold >layui-bg-red<#else>layui-bg-green</#if>">
                                <div class="panel-body">
                                    <div class="panel-title">
                                        <span class="label pull-right layui-bg-blue">最小响应时长</span>
                                    </div>
                                    <div class="panel-content">
                                        <h1 class="no-margins">${system.minRunTime}</h1>
                                        <small>最小响应（ms）</small>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <fieldset class="layui-elem-field layui-field-title" style="margin-top: 30px;">
                <legend>请求统计</legend>
            </fieldset>

            <div class="layuimini-main layui-top-box">
                <div class="layui-row layui-col-space10">

                    <div class="layui-col-md3">
                        <div class="col-xs-6 col-md-3">
                            <div class="layui-card top-panel layui-bg-gray">
                                <div class="layui-card-header">正常响应统计</div>
                                <div class="layui-card-body">
                                    <div class="layui-row layui-col-space5">
                                        <div class="layui-col-xs7 layui-col-md7 top-panel-number">${system.normalStatistics.todayStatistics}</div>
                                        <div class="layui-col-xs5 layui-col-md5 top-panel-tips">
                                            昨天 <a style="color: deeppink">${system.normalStatistics.yesterdayStatistics}</a><br>
                                            前天 <a style="color: deeppink">${system.normalStatistics.theDayBeforeYesterdayStatistics}</a>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>

                    <div class="layui-col-md3">
                        <div class="col-xs-6 col-md-3">
                            <div class="layui-card top-panel layui-bg-gray">
                                <div class="layui-card-header">延迟响应统计</div>
                                <div class="layui-card-body">
                                    <div class="layui-row layui-col-space5">
                                        <div class="layui-col-xs7 layui-col-md7 top-panel-number">${system.delayStatistics.todayStatistics}</div>
                                        <div class="layui-col-xs5 layui-col-md5 top-panel-tips">
                                            昨天 <a style="color: deeppink">${system.delayStatistics.yesterdayStatistics}</a><br>
                                            前天 <a style="color: deeppink">${system.delayStatistics.theDayBeforeYesterdayStatistics}</a>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <div class="layui-tab-item">
            <div class="layui-collapse" lay-filter="linkLayer">
                <#list list as runtime>
                <div class="layui-colla-item" >
                    <h2 class="layui-colla-title" id="${runtime.className}.${runtime.methodName}">
                        ${runtime.className}#${runtime.methodName}&nbsp
                        <span class="layui-badge <#if runtime.avgRunTime gt config.timeThreshold >layui-bg-red
                <#else>layui-bg-green
                </#if>">平均响应 ${runtime.avgRunTime} 毫秒</span>
                    </h2>
                    <div  class="layui-colla-content viewer" style="width: 98%;height:600px;">
                    </div>
                </div>
            </#list>
        </div>
    </div>
</div>
</div>
</div>


<script src="/layui/layui.js" charset="utf-8"></script>
<script src="/jquery.min.js"></script>
<script src="/echarts.min.js"></script>
<script src="/config.js"></script>
<script >
    $(document).ready(function() {
        var threshold = "${config.timeThreshold}"
        layui.use(['element', 'layer'], function () {
            var element = layui.element;

            element.on('collapse(linkLayer)', function (data) {
                id = data.title['0'].id
                show(data.content['0'],id)
            });
        });

        function show(element,name) {
            var myChart = echarts.init(element);
            myChart.showLoading();
            $.get('${ctx.contextPath}/linkTrack/getTree?methodName=' + name, function (data) {
                console.log(data)
                myChart.hideLoading();
                echarts.util.each(data.children, function (datum, index) {
                    index % 2 === 0 && (datum.collapsed = true);
                });
                myChart.setOption(option = getOption(data,threshold));
            });
        }

        $('.site-doc-icon').delegate("li",'click', function(data){
        });
    })
</script>

</body>
</html>