$(document).ready(()=> main());

const genders_dct ={
  "Male":"мужской", 
  "Female":"женский", 
  "":"?", 
  null:"?"
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
    loadPatients(patients);
}

function setFormProperties(){

   $("#alive-checkbox").change(  () => {
    if($("#alive-checkbox").is(":checked")){
      $(".deathdate-fields").prop("disabled",false);
      $(".deathdate-fields").css("visibility", "visible");
    }
    else{
      $(".deathdate-fields").prop("disabled",true);
      $(".deathdate-fields").css("visibility", "hidden");

    }
});
}


function loadPatients(data){
  
  data.forEach((patient) => {
    let div = `
    <div id="patient-${patient.id}" class="patient-card">
      <p class="id-placeholder">${patient.id}</p>
      <p>${patient.lastname} ${patient.name} ${patient.patronymic}</p>
      <p>${patient.birthdate}</p>
      <p>${patient.mainDiagnosis}</p>
    </div>
    `

    
    $("#patients-area").append(div);
    $(`#patient-${patient.id}`).on( "click", ()=>{

      patientToForm(patient);
    })
  })
  $("#patients-area").append(`<button id = "new-patient">Новый пациент</button>`);
  
}



function patientToForm(patient){
  console.log()
  $("#id-field").text(patient.id);
  $("#name-field").val(patient.name);
  $("#lastname-field").val(patient.lastname);
  $("#patron-field").val(patient.patronymic);
  $("#birth-field").val(patient.birthdate);
  $("#gender-field").val(patient.gender);
  $("#diagnosis-field").val(patient.mainDiagnosis);
  $("#diagnosisOther-field").val(patient.otherDiagnosis);
  $("#alive-checkbox").val(patient.alive);
  $("#deathdate-field").val(patient.deathdate);
  $("#info").val(patient.info); 
}