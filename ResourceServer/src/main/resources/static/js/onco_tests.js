import {addPatientFormToDoc, getPatientFromForm, patientToForm,clearForm} from "./html_constructors/patient_info_form.js";
import {getPatientById, getFullGroupedCatalog, getAllTestByPatientId} from "./requests.js"
import testsToTestForm from './html_constructors/TestsForm.js';
let currentPatient ={};
let tests = {};
$(document).ready(()=> main());

function main(){
  fillPatient();
  console.log(currentPatient);
  fillCatalog();
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

  getFullGroupedCatalog((data) => console.log(data));

}


function getPatientTests(id){
  getAllTestByPatientId(id, (data)=>console.log(data));
}










const catalog = {
    "Hematological": [
      {
        "id": 1,
        "name": "Лейкоциты",
        "additionalName": "WBC",
        "unit": "10E9/л",
        "refMin": 4.6,
        "refMax": 7.1,
        "researchType": "Hematological"
      },
      {
        "id": 2,
        "name": "Лимфоциты",
        "additionalName": "LYMF",
        "unit": "10E9/л",
        "refMin": 1.6,
        "refMax": 2.4,
        "researchType": "Hematological"
      },
      {
        "id": 3,
        "name": "Моноциты",
        "additionalName": "MON",
        "unit": "10E9/л",
        "refMin": 0,
        "refMax": 0.8,
        "researchType": "Hematological"
      },
      {
        "id": 4,
        "name": "Нейтрофилы",
        "additionalName": "NEU",
        "unit": "10E9/л",
        "refMin": 2,
        "refMax": 5.5,
        "researchType": "Hematological"
      },
      {
        "id": 5,
        "name": "Эозинофилы",
        "additionalName": "EOS",
        "unit": "10E9/л",
        "refMin": 0,
        "refMax": 0.7,
        "researchType": "Hematological"
      },
      {
        "id": 6,
        "name": "Базофилы",
        "additionalName": "BAS",
        "unit": "10E9/л",
        "refMin": 0,
        "refMax": 0.2,
        "researchType": "Hematological"
      },
      {
        "id": 7,
        "name": "Гемоглобин",
        "additionalName": "HGB",
        "unit": "г/л",
        "refMin": 110,
        "refMax": 160,
        "researchType": "Hematological"
      },
      {
        "id": 8,
        "name": "Тромбоциты",
        "additionalName": "PLT",
        "unit": "10E9/л",
        "refMin": 180,
        "refMax": 320,
        "researchType": "Hematological"
      }
    ],
    "Cytokine": [
      {
        "id": 18,
        "name": "CD3+IFNy+",
        "additionalName": "стимулированный",
        "unit": "%",
        "refMin": 10.9,
        "refMax": 18.6,
        "researchType": "Cytokine"
      },
      {
        "id": 19,
        "name": "CD3+IFNy+",
        "additionalName": "спонтанный",
        "unit": "%",
        "refMin": 0,
        "refMax": 0.5,
        "researchType": "Cytokine"
      },
      {
        "id": 20,
        "name": "CD3+TNFa+",
        "additionalName": "стимулированный",
        "unit": "%",
        "refMin": 31.57,
        "refMax": 45.7,
        "researchType": "Cytokine"
      },
      {
        "id": 21,
        "name": "CD3+TNFa+",
        "additionalName": "спонтанный",
        "unit": "%",
        "refMin": 0,
        "refMax": 0.9,
        "researchType": "Cytokine"
      },
      {
        "id": 22,
        "name": "CD3+IL2+",
        "additionalName": "стимулированный",
        "unit": "%",
        "refMin": 31.57,
        "refMax": 45.7,
        "researchType": "Cytokine"
      },
      {
        "id": 23,
        "name": "CD3+IL2+",
        "additionalName": "спонтанный",
        "unit": "%",
        "refMin": 0,
        "refMax": 0.5,
        "researchType": "Cytokine"
      }
    ],
    "Immunological": [
      {
        "id": 9,
        "name": "Общие T-лимфоциты",
        "additionalName": "CD45+CD3+",
        "unit": "10E9/л",
        "refMin": 0.8,
        "refMax": 2.2,
        "researchType": "Immunological"
      },
      {
        "id": 10,
        "name": "Общие B-лимфоциты",
        "additionalName": "CD45+CD19+",
        "unit": "10E9/л",
        "refMin": 0.2,
        "refMax": 0.4,
        "researchType": "Immunological"
      },
      {
        "id": 11,
        "name": "Общие T-хелперы",
        "additionalName": "CD45+CD3+CD4+",
        "unit": "10E9/л",
        "refMin": 0.7,
        "refMax": 1.1,
        "researchType": "Immunological"
      },
      {
        "id": 12,
        "name": "Общие T-цитотоксические лимфоциты",
        "additionalName": "CD45+CD3+CD8+",
        "unit": "10E9/л",
        "refMin": 0.5,
        "refMax": 0.9,
        "researchType": "Immunological"
      },
      {
        "id": 13,
        "name": "Общие NK-клетки",
        "additionalName": "CD45+CD3+",
        "unit": "10E9/л",
        "refMin": 0.15,
        "refMax": 0.5,
        "researchType": "Immunological"
      },
      {
        "id": 14,
        "name": "NK-клетки цитокинпродуцирующие",
        "additionalName": " CD45+CD3-CD16brightCD56dim",
        "unit": "%",
        "refMin": 93.75,
        "refMax": 97.5,
        "researchType": "Immunological"
      },
      {
        "id": 15,
        "name": "Циркулирующие иммунные комплексы",
        "additionalName": "-",
        "unit": "ед.",
        "refMin": 40,
        "refMax": 70,
        "researchType": "Immunological"
      },
      {
        "id": 16,
        "name": "НСТ-тест",
        "additionalName": "спонтанный",
        "unit": "%",
        "refMin": 6,
        "refMax": 12,
        "researchType": "Immunological"
      },
      {
        "id": 17,
        "name": "НСТ-тест",
        "additionalName": "стимулированный",
        "unit": "%",
        "refMin": 24,
        "refMax": 80,
        "researchType": "Immunological"
      }
    ]
  }