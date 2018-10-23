<template>
    <div>
        <!-- 头部标签 -->
        <header-label :data="headerLabel"></header-label>
        <!-- 动画 -->
        <div class="wrapper wrapper-content animated fadeInRight">
        
        
        <!-- 表单 -->
            <div class="row">
              <div class="col-lg-12">
                <div class="ibox tt-from-table">
                  <div class="ibox-content">
                      <form role="form" class="form-inline validation">
                          <div class="btn-toolbar pull-left" role="toolbar">
                              <div class="btn-group">
                                   <a href="/report/download" shiro:hasPermission="report:download"><button  class="btn btn-primary" type="button">excel导出</button></a>
                              </div>
                          </div>
                      </form>
                      <div class="clearfix"></div>
                  </div>
                </div>
              </div>
            </div>
            
            
            <div class="row">
                <div class="col-lg-10">
                    <div class="ibox">
                        <div class="ibox-content">
                            <div>
                                <canvas id="doughnutChart" height="140"></canvas>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<script type="application/javascript">
//路由配置
RouteConfig.deploy({
  data: function() {
    return {
      headerLabel: {
        name: "资产总揽",
        path: {
          parent: [{ url: "/", name: "Home" }, { name: "Report" }],
          active: "Overall"
        }
      },
      pieData: {
        labels: ["正常", "领用", "报废", "维修"],
        datasets: [
          {
            data: [300, 50, 100, 100],
            backgroundColor: ["#a3e1d4", "#dedede", "#b5b8cf", "#b7cfc8"]
          }
        ]
      },
      myPieChart: null
    };
  },
  computed: {},
  created: function() {
    let self = this;
  },
  mounted: function() {
    let self = this;
    let doughnutOptions = {
      responsive: true
    };
    let ctx4 = document.getElementById("doughnutChart").getContext("2d");
    self.myPieChart = new Chart(ctx4, {
      type: "doughnut",
      data: self.pieData,
      options: doughnutOptions
    });

    Server.report.getOverall.execute(data => {
      let temp = [];
      $.each(data.object, function(key, value) {
        temp.push(value);
      });
      self.pieData.datasets[0].data = temp;
      self.myPieChart.update();
    });
  },
  methods: {}
});
</script>