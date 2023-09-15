import {addPatientFormToDoc, getPatientFromForm, patientToForm,clearForm} from "./html_constructors/patientInfoForm.js";
import {getPatientById, getFullGroupedCatalog, getAllTestByPatientId,getAllTestResultsByTestId} from "./requests.js"
import testsToTestForm from './html_constructors/TestsForm.js';
import catalogToForm from "./html_constructors/CatalogForm.js";
import { drawBChart } from "./charts.js";

let currentPatient ={};
let tests = {};
let currentResults = {};
$(document).ready(()=> main());

function main(){
  $(`.results-area`).on("change", ()=>ReedrawCharts());
  fillPatient();
  console.log(currentPatient);
  fillCatalog();
  fillResults();
}

function fillPatient(){
  let id = document.location.href.split("/").pop();
  addPatientFormToDoc(".patients-area");
  getPatientById(id, (data)=> {
    currentPatient = data;
    patientToForm(data);
    console.log(data);
    getAllTestByPatientId(data.id, (foundTests)=>{
      console.log(foundTests);
      tests = foundTests;
      fillTests(tests);
    })
  });

}

function fillTests(tests){
  testsToTestForm(".onco-tests-area", tests);
}

function fillCatalog(){
  //catalogToForm(catalog);
  getFullGroupedCatalog((data) => {
    catalogToForm(data)
    
  
  });

}

function fillResults(){
  $(`#current-test-date`).on("change", ()=>{
    getAllTestResultsByTestId($(`#current-test-id`).val(), (data)=>{

      currentResults = data;
      resultsToCatalogForm(currentResults);
      $(`.index-line`).trigger("indexiesChanged");
    })


    
  });

}

function resultsToCatalogForm(results){
  results.forEach(res =>{
    $(`#result-for-param-${res.parameter.id}`).val(res.value);

  })
}

function ReedrawCharts(){
  console.log("Reedraw Graphs")
  drawBChart();
}