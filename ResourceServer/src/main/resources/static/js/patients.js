$(document).ready(()=> main());

const baseUrl = 'http://localhost:8080';
const genders_dct ={
  "Male":"мужской", 
  "Female":"женский", 
  "":"?", 
  null:"?",
  "мужской":"Male",
  "женский":"Female"
} 


let patients = [
    {
      "id": 1,
      "name": "ИВАН",
      "lastname": "ИВАНОВ",
      "patronymic": "ИВАНОВИЧ",
      "birthdate": "1980-10-01",
      "deathdate": null,
      "alive": true,
      "mainDiagnosis": "C50",
      "otherDiagnosis": null,
      "info": null,
      "gender": "Male"
    },
    {
      "id": 2,
      "name": "Андрей",
      "lastname": "Андреевич",
      "patronymic": "Аендреев",
      "birthdate": "1999-01-18",
      "deathdate": "2010-02-20",
      "alive": false,
      "mainDiagnosis": "C50",
      "otherDiagnosis": null,
      "info": null,
      "gender": "Female"
    }
  ]


function main(){
    //let patiets = получить список всех пациентов
    setFormProperties();
    loadPatients();
}

function setFormProperties(){
  $(`#save-patient`).on( "click", ()=>{ savePatient(); });
  

   $("#alive-checkbox").change(  () => {
    console.log("ASDASDSD");
    if($("#alive-checkbox").is(":checked")){
      //$(".deathdate-fields").prop("disabled",false);
      $(".deathdate-fields").css("visibility", "visible");
    }
    else{
      //$(".deathdate-fields").prop("disabled",true);
      $(".deathdate-fields").css("visibility", "hidden");

    }
});

}

async function loadPatients(){
  
  clrPatientForm();
  const response =  await fetch(baseUrl+"/patients/all");
  let data = await response.json();
  //let data = patients();

  data.forEach((patient) => {
    console.log(patient);
    let div = `
    <div class="patient-line">
      <div id="patient-${patient.id}" class="patient-card ">
        <p class="id-placeholder">${patient.id}</p>
        <p>${patient.lastname ?? "-"} ${patient.name ?? "-" } ${patient.patronymic ?? "-"}</p>
        <p>${patient.birthdate ?? "-"}</p>
        <p>${patient.mainDiagnosis ?? "-"}</p>
      </div>
      <div id="patient-edit-btn-${patient.id}" class="patient-edit-btn">Анализы<div>
    </div>
    `

    
    $("#patients-area").append(div);
    $(`#patient-${patient.id}`).on( "click", ()=>{patientToForm(patient);});
    $(`#patient-edit-btn-${patient.id}`).on( "click", ()=>{console.log("TO TEST")});
  })
  $("#patients-area").append(`<button id = "new-patient">Новый пациент</button>`);
  
  $(`#new-patient`).on( "click", ()=>{

    clrPatientForm();
  })
}

function clrPatientForm(){

  $("#id-field").val(-1);
  $("#name-field").val("");
  $("#lastname-field").val("");
  $("#patron-field").val("");
  $("#birth-field").val("");
  //$("#gender-field").val(patient.gender);
  $("#diagnosis-field").val("");
  $("#diagnosisOther-field").val("");
  $("#alive-checkbox").prop("checked",false);
  $("#alive-checkbox").trigger("change");
  $("#deathdate-field").val("");
  $("#info").val(""); 
}


function patientToForm(patient){
  $("#id-field").val(patient.id);
  $("#name-field").val(patient.name);
  $("#lastname-field").val(patient.lastname);
  $("#patron-field").val(patient.patronymic);
  $("#birth-field").val(patient.birthdate);
  $("#gender-field").val(patient.gender);
  $("#diagnosis-field").val(patient.mainDiagnosis);
  $("#diagnosisOther-field").val(patient.otherDiagnosis);
  $("#alive-checkbox").prop("checked",!patient.alive);
  $("#alive-checkbox").trigger("change");
  $("#deathdate-field").val(patient.deathdate);
  $("#info").val(patient.info); 
}

function savePatient(){ 
  let pat = {}
  if($("#id-field").val() > 0) pat["id"] = $("#id-field").val();
  pat["name"] = $("#name-field").val();
  pat["lastname"] = $("#lastname-field").val();
  pat["patronymic"] = $("#patron-field").val();
  pat["birthdate"] = $("#birth-field").val();
  pat["gender"] =$("#gender-field").val();
  pat["alive"] = !$("#alive-checkbox").is(":checked");
  if($("#deathdate-field").val() != "") 
    pat["deathdate"] = $("#deathdate-field").val();   // или null
  pat["mainDiagnosis"] = $("#diagnosis-field").val();
  pat["otherDiagnosis"] = $("#diagnosisOther-field").val();
  pat["info"] = $("#info").val();

  console.log(pat);

  for (key in pat) {
    if(pat[key]==="") pat[key] = null;
  }
  
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
  console.log(data);
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
  clrPatientForm()
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
  clrPatientForm()
  loadPatients();
  return response.json();
}
