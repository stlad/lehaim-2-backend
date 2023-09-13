
const baseUrl = 'http://localhost:8080';

function getPatientById(id, dataFunc){

    $.ajax({
        url: baseUrl + '/patients/' + id,
        method: 'get',
        dataType: 'json',
        success: function(data){
            dataFunc(data);
        }
    });
}

function getFullGroupedCatalog(dataFunc){
    $.ajax({
        url: baseUrl + '/catalog/all/grouped',
        method: 'get',
        dataType: 'json',
        success: function(data){
            dataFunc(data);
        }
    });
}

function getAllTestByPatientId(id, dataFunc){
    $.ajax({
        url: baseUrl + '/results/tests/'+id+'/all',
        method: 'get',
        dataType: 'json',
        success: function(data){
            dataFunc(data);
        }
    });

}


export {getPatientById, getFullGroupedCatalog,getAllTestByPatientId}