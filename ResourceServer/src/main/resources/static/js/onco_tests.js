import {addPatientFormToDoc, getPatientFromForm, patientToForm,clearForm} from "./html_constructors/patientInfoForm.js";
import {getPatientById, getFullGroupedCatalog, getAllTestByPatientId,getAllTestResultsByTestId, saveTest} from "./requests.js"
import testsToTestForm from './html_constructors/TestsForm.js';
import catalogToForm from "./html_constructors/CatalogForm.js";
import { updateBChart } from "./charts.js";

let currentPatient ={};
let tests = {};
let currentResults = {};
$(document).ready(()=> main());

function main(){
  $(`.results-area`).on("change", ()=>ReedrawCharts());
  $(`.results-area`).on("testsfilled", ()=>ReedrawCharts());

  fillPatient();
  console.log(currentPatient);
  fillCatalog();
  fillResults();

  $("#new-test-btn").on("click", ()=>{
      if($("#save-test-date").val() == null) return;
      saveTest($("#save-test-date").val(),currentPatient.id, (savedTest)=>{
        $("#save-test-date").val(null);
        $(`.onco-tests-area`).trigger("refilltests");
      })
  });

  $(`.onco-tests-area`).on("refilltests", ()=>{
    $(".onco-tests-area").empty();
    getAllTestByPatientId(currentPatient.id, (foundTests)=>{
      tests = foundTests;
      fillTests(tests);
    })
  });

}

function fillPatient(){
  let id = document.location.href.split("/").pop();
  addPatientFormToDoc(".patients-area");
  getPatientById(id, (data)=> {
    currentPatient = data;
    patientToForm(data);
    getAllTestByPatientId(data.id, (foundTests)=>{
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
  updateBChart();
}