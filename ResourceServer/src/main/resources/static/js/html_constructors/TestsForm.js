
export default function testsToTestForm(selector, tests){
    tests.forEach((test) => {
        console.log(test);
        let season = getSeason(test.testDate);
        console.log(season);
        let age = getAge(test.patientOwner.birthdate, test.testDate);
        console.log(age);
        let div=`
        <div class="test-card" id="test-card-${test.id}">
            
            <input type="number" id="id-field" class="id-placeholder" value="${test.id}">
            ${test.testDate} (${age} лет)<br>
            Сезон: ${season};
          </div>
        `
        $(selector).append(div);
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