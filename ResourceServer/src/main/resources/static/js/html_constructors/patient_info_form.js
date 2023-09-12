


function addPatientFormToDoc(selector){
  console.log('second');
   let form = createPatientForm();
    
    $(selector).append(form);
    setFormProperties();
}

function setFormProperties(){
    $(`#save-patient`).on( "click", ()=>{ getPatientFromForm(); });
    //$(".id-placeholder").css("display","none");
    
     $("#alive-checkbox").change(  () => {
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
  
function createPatientForm(){
    let str = `

    <div class="patient-info">

    <form class="patient_edit_form">
        <input type="number" id="id-field" class="id-placeholder" value="-1"> 
        <input type="text" id="lastname-field" class="form-control  field" placeholder="Фамилия"><br>
        <input type="text" id="name-field" class="form-control  field" placeholder="Имя"><br>
        <input type="text" id="patron-field" class="form-control  field" placeholder="Отчество"><br>
        <input type="date" id="birth-field" valuename="date" class="form-control field">
        <select  id="gender-field" class=" form-control  field gender-field">
            <option value="Male">мужской</option>
            <option value="Female">женский</option>
        </select>
        <br>
        <input type="text" id="diagnosis-field" class="form-control  field" placeholder="Диагноз основной"><br>
        <input type="text" id="diagnosisOther-field" class="form-control  field" placeholder="Диагноз дополнительный"><br>
  
        <label for="checkbox">Мертв:</label>
            <input type="checkbox" id="alive-checkbox" >

        <div class="inline-fields deathdate-fields">
            <label for="deathdate-field" id="deathdate-field-label">Дата смерти:</label>
            <input type="date" id="deathdate-field" valuename="deathdate" class="form-control field">

        </div>
        <br>
        <textarea id="info" placeholder="Доп. Информация"></textarea><br>
    </form>
    <input type="submit" id = "save-patient" class="form-control  field" placeholder="Сохранить"><br>
    </div>`
    return str;
}

function getPatientFromForm(){ 
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
  
    for (var key in pat) {
      if(pat[key]==="") pat[key] = null;
    }
    return pat;
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

function clearForm(){
    patientToForm(tempateEmptyPatient);
}

const tempateEmptyPatient = {
    "id": -1,
    "name": "",
    "lastname": "",
    "patronymic": "",
    "birthdate": "",
    "deathdate": "",
    "alive": true,
    "mainDiagnosis": "",
    "otherDiagnosis": "",
    "info": "",
    "gender": "Male"
}

export {patientToForm, getPatientFromForm, addPatientFormToDoc,clearForm}