import { catalogData } from "./html_constructors/CatalogForm.js";

let bChart = setBChart();
let tChart = setTChart();
let CytocineChart = setCytocineChart();

//-----------------------------------------
function setTChart(){

}

function updateTChart(){

}

function getTChartValues(){

}

//-----------------------------------------

function setCytocineChart(){

}

function updateCytocineChart(){

}

function getTCytocineChart(){

}


//-----------------------------------------

function updateBChart(){
  var option;
  var values = getBChartValues(),

option = {
  title: {
    text: 'Относительные параметры B - клеточного звена иммунитета'
  },
  legend: {
    data: ['', '']
  },
  radar: {
    // shape: 'circle',
    indicator: [
      { name: 'NEU/CD19' },
      { name: 'CD19/CD4' },
      { name: 'CD19/CD8' },
      { name: 'NEU/LYMF' }
    ]
  },
  series: [
    {
      name: '',
      type: 'radar',
      data: [
        {
          label: { show: true, },
          value: [1,1,1,1],
          name: 'Нижние референтные значения',
          itemStyle: {
            color: '#F9713C'
          },
          lineStyle: {
            type: 'dashed'
          },
        },
        {
          label: { show: true, },
          value: [3,3,3,3],
          name: 'Верхние референтные значения',
          itemStyle: {
            color: '#F9713C'
          },
          lineStyle: {
            type: 'dashed'
          },
        },
        {
          label: { show: true, },
          value: values,
          name: 'Результат'
        }
      ]
    }
  ]
};
bChart.setOption(option);
}


function setBChart(){
  var chartDom = document.getElementById('B-chart');
   var myChart = echarts.init(chartDom, null, {
  width: 800,
  height: 800
});

var option;
option && myChart.setOption(option);
console.log(myChart);
return myChart;
}




function getBChartValues(){
  console.log(catalogData);
    let NEU = getValueFor("NEU");
    let LYMF = getValueFor("LYMF");
    let CD3 = getValueFor("CD45+CD3+");
    let CD4 = getValueFor("CD45+CD3+CD4+");
    let CD19 = getValueFor("CD45+CD19+");
    let CD8 = getValueFor("CD45+CD3+CD8+");
    console.log([NEU, LYMF, CD19,CD4,CD8]);
    let CD19_CD4 =  CD4 != 0 ? (CD19/CD4).toFixed(2) : 0;
    let CD19_CD8 =  CD8 != 0 ? (CD19/CD8).toFixed(2) : 0;
    let NEU_CD19 =  CD19 != 0 ? (NEU/CD19).toFixed(2) : 0;
    let NEU_LYMF = LYMF != 0 ? (NEU/LYMF).toFixed(2) : 0;
    console.log([NEU_CD19, CD19_CD4, CD19_CD8, NEU_LYMF]);

    return [NEU_CD19, CD19_CD4, CD19_CD8, NEU_LYMF];
    
}

//-----------------------------------------



function getValueFor(name){
  //console.log(catalogData[name].id);
  //console.log($(`#result-for-param-${catalogData[name].id}`).val());
    return $(`#result-for-param-${catalogData[name].id}`).val();
}

function getMinFor(name){
    return catalogData[name].min;
}
function getMaxFor(name){
    return catalogData[name].max;
}

export {updateBChart, updateTChart, }