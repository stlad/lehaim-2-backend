import {addPatientFormToDoc, getPatientFromForm, patientToForm,clearForm} from "./html_constructors/patientInfoForm.js";
import {getPatientById, getFullGroupedCatalog, getAllTestByPatientId} from "./requests.js"
import testsToTestForm from './html_constructors/TestsForm.js';
import catalogToForm from "./html_constructors/CatalogForm.js";


let currentPatient ={};
let tests = {};
$(document).ready(()=> main());

function main(){
  fillPatient();
  console.log(currentPatient);
  fillCatalog();

  $(`.results-area`).on("change", ()=>console.log("Reedraw Graphs"));
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