import { catalogData } from "./html_constructors/CatalogForm.js";

let bChart = setBChart();

function getValueFor(name){
  console.log(catalogData[name].id);
  console.log($(`#result-for-param-${catalogData[name].id}`).val());
    return $(`#result-for-param-${catalogData[name].id}`).val();
}

function getMinFor(name){
    return catalogData[name].min;
}
function getMaxFor(name){
    return catalogData[name].max;
}






function setBChart(){
const ctx = document.getElementById('B-chart').getContext('2d');

let chart = new Chart(ctx, {
    type: 'radar',
    data: {
      labels: ['NEU/CD19','CD19/CD4','CD19/CD8', 'NEU/LYMF' ],
      datasets: [{
        label: 'Нижние референтные значения',
        data: [0,0,0,0],
        borderWidth: 1, // С БЭКЕНДА
        fill: false,
        borderColor: 'rgb(54, 162, 235)',
      },
      {
        label: 'Результат',
        data: [],
        borderWidth: 1, // С БЭКЕНДА
        fill: false,
        borderColor: 'rgb(255, 99, 132)'
      },
      {
        label: 'Верхние референтные значения',
        data: [1, 1,1,1 ],
        borderWidth: 1, // С БЭКЕНДА
        fill: false,
        borderColor: 'rgb(54, 162, 235)',
      },
    
    ]
    }, 
    options: {
        normalized: true,
        plugins:{
          title: {
            display: true,
            text: 'Относительные параметры B - клеточного звена иммунитета',
            padding: {
                top: 10,
                bottom: 30
            }
        }
        },
        elements: {
          line: {
            borderWidth: 3
          }
        }
      },
  });
  chart.resize(600,600);
  return chart;
}




function drawBChart(){
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


    bChart.data.datasets[1].data = [NEU_CD19, CD19_CD4, CD19_CD8, NEU_LYMF];
    bChart.update();
}



function drawTChart(){

}


function drawCytocineChart(){

}


export {drawBChart, drawTChart, drawCytocineChart}