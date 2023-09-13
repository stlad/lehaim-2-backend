import {addPatientFormToDoc, getPatientFromForm, patientToForm,clearForm} from "./html_constructors/patient_info_form.js";


$(document).ready(()=> main());

const baseUrl = 'http://localhost:8080';


function main(){
    addPatientFormToDoc("#patient-info-form-area");
    $(`#save-patient`).on( "click", ()=>{ savePatient(); });
    loadPatients();
}

async function loadPatients(){
  
    clearForm();
    const response =  await fetch(baseUrl+"/patients/all");
    let data = await response.json();
    //let data = patients();
  
    data.forEach((patient) => {
      console.log(patient);
      let div = `
      <div class="patient-line">
      <div id="patient-delete-btn-${patient.id}" class="patient-delete-btn">X</div>
  
        <div id="patient-${patient.id}" class="patient-card ">
          <p class="id-placeholder">${patient.id}</p>
          <p>${patient.lastname ?? "-"} ${patient.name ?? "-" } ${patient.patronymic ?? "-"}</p>
          <p>${patient.birthdate ?? "-"}</p>
          <p>${patient.mainDiagnosis ?? "-"}</p>
        </div>
        <a id="patient-to-test-btn-${patient.id}" class="patient-to-test-btn" href="/tests/${patient.id}">Анализы</a>
      </div>
      `;
  
      
      $("#patients-area").append(div);
      $(`#patient-delete-btn-${patient.id}`).on( "click", ()=>{deletePatient(patient);});
      $(`#patient-${patient.id}`).on( "click", ()=>{patientToForm(patient);});
      //$(`#patient-to-test-btn-${patient.id}`).on( "click", ()=>{window.location.repalce(`/tests/${patient.id}`)});
    })
    $("#patients-area").append(`<button id = "new-patient">Новый пациент</button>`);
    
    $(`#new-patient`).on( "click", ()=>{
  
      clearForm();
    })
  }

function savePatient(){ 
    let pat = getPatientFromForm();
    if($("#id-field").val() > 0){
      updatePatient(pat);
    }
    else{
      saveNewPatient(pat);
    }
}




async function getAllPatients(){
    const response =  await fetch(baseUrl+"/patients/all");
    let data = await response.json();
    return data;
  }
  
  async function saveNewPatient(patient){
    let requestBody = JSON.stringify(patient);
    console.log(requestBody);
    const response = await fetch(baseUrl+"/patients/", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: requestBody, 
    });
    $("#patients-area").empty();
    clearForm()
    loadPatients();
    return response.json(); 
  }
  
  async function updatePatient(patient){
    let requestBody = JSON.stringify(patient);
    console.log(requestBody);
    const response = await fetch(baseUrl+"/patients/", {
      method: "PUT",
      headers: {
        "Content-Type": "application/json",
      },
      body: requestBody, 
    });
    $("#patients-area").empty();
    clearForm()
    loadPatients();
    return response.json();
  }
  
  async function deletePatient(patient){
    let requestBody = JSON.stringify(patient);
    console.log(requestBody);
    const response = await fetch(baseUrl+"/patients/"+patient.id, {
      method: "DELETE",
      headers: {
        "Content-Type": "application/json",
      },
    });
  
    $("#patients-area").empty();
    clearForm()
    loadPatients();
    return response.json();
  }
  