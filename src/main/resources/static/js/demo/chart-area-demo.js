// Set new default font family and font color to mimic Bootstrap's default styling
Chart.defaults.global.defaultFontFamily = 'Nunito', '-apple-system,system-ui,BlinkMacSystemFont,"Segoe UI",Roboto,"Helvetica Neue",Arial,sans-serif';
Chart.defaults.global.defaultFontColor = '#858796';

function number_format(number, decimals, dec_point, thousands_sep) {
  // *     example: number_format(1234.56, 2, ',', ' ');
  // *     return: '1 234,56'
  number = (number + '').replace(',', '').replace(' ', '');
  var n = !isFinite(+number) ? 0 : +number,
      prec = !isFinite(+decimals) ? 0 : Math.abs(decimals),
      sep = (typeof thousands_sep === 'undefined') ? ',' : thousands_sep,
      dec = (typeof dec_point === 'undefined') ? '.' : dec_point,
      s = '',
      toFixedFix = function(n, prec) {
        var k = Math.pow(10, prec);
        return '' + Math.round(n * k) / k;
      };
  // Fix for IE parseFloat(0.55).toFixed(0) = 0;
  s = (prec ? toFixedFix(n, prec) : '' + Math.round(n)).split('.');
  if (s[0].length > 3) {
    s[0] = s[0].replace(/\B(?=(?:\d{3})+(?!\d))/g, sep);
  }
  if ((s[1] || '').length < prec) {
    s[1] = s[1] || '';
    s[1] += new Array(prec - s[1].length + 1).join('0');
  }
  return s.join(dec);
}

/**
 * 1줄 라인차트 (원래 함수)
 * @param {HTMLCanvasElement} ctx
 * @param {Array<string>}     labels
 * @param {Array<number>}     data
 * @param {string}            datasetLabel
 */
function buildLineChart(ctx, labels, data, datasetLabel) {
  if (!ctx) return;

  // 같은 캔버스에 이전 차트가 있으면 파괴(DOM 속성에 저장)
  if (ctx._chartInstance) {
    try { ctx._chartInstance.destroy(); } catch (e) {}
  }

  var nums = (data || []).map(Number).filter(function(v){ return isFinite(v); });
  var pad = 1;
  var yMin = nums.length ? Math.min.apply(null, nums) : 0;
  var yMax = nums.length ? Math.max.apply(null, nums) : 1;
  var suggestedMin = Math.floor(yMin) - pad;
  var suggestedMax = Math.ceil(yMax) + pad;

  var myLineChart = new Chart(ctx, {
    type: 'line',
    data: {
      labels: labels,
      datasets: [{
        label: datasetLabel,
        lineTension: 0.3,
        backgroundColor: "rgba(78, 115, 223, 0.05)",
        borderColor: "rgba(78, 115, 223, 1)",
        pointRadius: 3,
        pointBackgroundColor: "rgba(78, 115, 223, 1)",
        pointBorderColor: "rgba(78, 115, 223, 1)",
        pointHoverRadius: 3,
        pointHoverBackgroundColor: "rgba(78, 115, 223, 1)",
        pointHoverBorderColor: "rgba(78, 115, 223, 1)",
        pointHitRadius: 10,
        pointBorderWidth: 2,
        data: data
      }]
    },
    options: {
      maintainAspectRatio: false,
      layout: { padding: { left:10, right:25, top:25, bottom:0 } },
      scales: {
        xAxes: [{
          time: { unit: 'date' },
          gridLines: { display:false, drawBorder:false },
          ticks: { maxTicksLimit: 7 }
        }],
        yAxes: [{
          ticks: {
            suggestedMin: suggestedMin,
            maxTicksLimit: 5, padding: 10,
            callback: function(value){ return number_format(value); }
          },
          gridLines: {
            color: "rgb(234, 236, 244)",
            zeroLineColor: "rgb(234, 236, 244)",
            drawBorder: false,
            borderDash: [2],
            zeroLineBorderDash: [2]
          }
        }]
      },
      legend: { display: false },
      tooltips: {
        backgroundColor: "rgb(255,255,255)",
        bodyFontColor: "#858796",
        titleMarginBottom: 10,
        titleFontColor: '#6e707e',
        titleFontSize: 14,
        borderColor: '#dddfeb',
        borderWidth: 1,
        xPadding: 15, yPadding: 15,
        displayColors: false, intersect: false, mode: 'index', caretPadding: 10,
        callbacks: {
          label: function(tooltipItem, dataObj) {
            var dsLabel = dataObj.datasets[tooltipItem.datasetIndex].label || '';
            return dsLabel + ': ' + number_format(tooltipItem.yLabel);
          }
        }
      }
    }
  });

  ctx._chartInstance = myLineChart;
}

/**
 * 2줄 라인차트 (두 번째 데이터셋이 없어도 동작)
 * @param {HTMLCanvasElement} ctx
 * @param {Array<string>}     labels
 * @param {Array<number>}     data1
 * @param {Array<number>}     data2   // 없으면 null 또는 undefined
 * @param {string}            datasetLabel1
 * @param {string}            datasetLabel2
 */
function buildLineChart2(ctx, labels, data1, data2, datasetLabel1, datasetLabel2) {
  if (!ctx) return;

  if (ctx._chartInstance) {
    try { ctx._chartInstance.destroy(); } catch (e) {}
  }

  var arr1 = (data1 || []).map(Number).filter(function(v){ return isFinite(v); });
  var hasSecond = Array.isArray(data2);
  var arr2 = hasSecond ? (data2 || []).map(Number).filter(function(v){ return isFinite(v); }) : [];

  var nums = arr1.concat(hasSecond ? arr2 : []);
  var pad = 1;
  var yMin = nums.length ? Math.min.apply(null, nums) : 0;
  var yMax = nums.length ? Math.max.apply(null, nums) : 1;
  var suggestedMin = Math.floor(yMin) - pad;
  var suggestedMax = Math.ceil(yMax) + pad;

  var datasets = [
    {
      label: datasetLabel1,
      lineTension: 0.3,
      backgroundColor: "rgba(78, 115, 223, 0.05)",
      borderColor: "rgba(78, 115, 223, 1)",
      pointRadius: 3,
      pointBackgroundColor: "rgba(78, 115, 223, 1)",
      pointBorderColor: "rgba(78, 115, 223, 1)",
      pointHoverRadius: 3,
      pointHoverBackgroundColor: "rgba(78, 115, 223, 1)",
      pointHoverBorderColor: "rgba(78, 115, 223, 1)",
      pointHitRadius: 10,
      pointBorderWidth: 2,
      data: data1 || []
    }
  ];

  if (hasSecond) {
    datasets.push({
      label: datasetLabel2,
      lineTension: 0.3,
      backgroundColor: "rgba(28, 200, 138, 0.05)",
      borderColor: "rgba(28, 200, 138, 1)",
      pointRadius: 3,
      pointBackgroundColor: "rgba(28, 200, 138, 1)",
      pointBorderColor: "rgba(28, 200, 138, 1)",
      pointHoverRadius: 3,
      pointHoverBackgroundColor: "rgba(28, 200, 138, 1)",
      pointHoverBorderColor: "rgba(28, 200, 138, 1)",
      pointHitRadius: 10,
      pointBorderWidth: 2,
      data: data2 || []
    });
  }

  var myLineChart = new Chart(ctx, {
    type: 'line',
    data: { labels: labels, datasets: datasets },
    options: {
      maintainAspectRatio: false,
      layout: { padding: { left:10, right:25, top:25, bottom:0 } },
      scales: {
        xAxes: [{
          time: { unit: 'date' },
          gridLines: { display:false, drawBorder:false },
          ticks: { maxTicksLimit: 7 }
        }],
        yAxes: [{
          ticks: {
            suggestedMin: suggestedMin,
            suggestedMax: suggestedMax,
            maxTicksLimit: 5, padding: 10,
            callback: function(value){ return number_format(value); }
          },
          gridLines: {
            color: "rgb(234, 236, 244)",
            zeroLineColor: "rgb(234, 236, 244)",
            drawBorder: false,
            borderDash: [2],
            zeroLineBorderDash: [2]
          }
        }]
      },
      legend: { display: false }, // 필요하면 true
      tooltips: {
        backgroundColor: "rgb(255,255,255)",
        bodyFontColor: "#858796",
        titleMarginBottom: 10,
        titleFontColor: '#6e707e',
        titleFontSize: 14,
        borderColor: '#dddfeb',
        borderWidth: 1,
        xPadding: 15, yPadding: 15,
        displayColors: false, intersect: false, mode: 'index', caretPadding: 10,
        callbacks: {
          label: function(tooltipItem, dataObj) {
            var dsLabel = dataObj.datasets[tooltipItem.datasetIndex].label || '';
            return dsLabel + ': ' + number_format(tooltipItem.yLabel);
          }
        }
      }
    }
  });

  ctx._chartInstance = myLineChart;
}
