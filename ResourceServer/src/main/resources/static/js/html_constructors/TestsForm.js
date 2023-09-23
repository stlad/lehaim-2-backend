import {deleteTestById} from '../requests.js'

export default function testsToTestForm(selector, tests){
    tests.forEach((test) => {
        console.log(test);
        //console.log(test);
        let season = getSeason(test.testDate);
        //console.log(season);
        let age = getAge(test.patientOwner.birthdate, test.testDate);
        //console.log(age);
        let div=`
        <div style=" display: flex; flex-direction: row;">
        <div id="delete-test-${test.id}" class="test_btn" >
        X</div>
        <div class="test-card" id="test-card-${test.id}">
            
            <input type="number" id="id-field" class="id-placeholder" value="${test.id}">
            ${test.testDate} (${age} лет)<br>
            Сезон: ${season};
          </div>
        </div>
        `
        $(selector).append(div);
        $(`#test-card-${test.id}`).on("click", ()=>{
            $(`#current-test-id`).val(test.id);
            $(`#current-test-date`).val(test.testDate);
            $(`#current-test-date`).trigger("change");
            $(`.results-area`).trigger("change");
            $(`.results-area`).trigger("testsfilled");
        })

        $(`#delete-test-${test.id}`).on("click",()=>{
            deleteTestById(test.id, null);
            $(selector).trigger("refilltests");
            }
        )
    });


}

function getAge(birth, moment){
    var birthDate = new Date(birth);
    var last = new Date(moment);
    var age = last.getFullYear() - birthDate.getFullYear();
    return age
}

function getSeason(moment){
    var date = new Date(moment);
    var month = date.getMonth(); // [2:7] - весна, [0:1]+[8:11] - осень
    if(month >= 2 && month <= 7)
        return "Весна";
    else
        return "Осень";
}


function clrForm(selector){
    $(selector).empty();
}