
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


function getAllTestResultsByTestId(id, dataFunc){
    $.ajax({
        url: baseUrl + '/results/'+id+'/all',
        method: 'get',
        dataType: 'json',
        success: function(data){
            dataFunc(data);
        }
    });

}

function deleteTestById(id,dataFunc){
    
    $.ajax({
        url: baseUrl + '/results/tests/'+id,
        method: 'delete',
        dataType: 'json',
        success: function(data){
            dataFunc(data);
        }
    });
}

function saveTest(testDate, ownerId, dataFunc){
    $.ajax({
        url: baseUrl + `/results/tests/new?owner_id=${ownerId}&test_date=${testDate}`,
        method: 'post',
        dataType: 'json',
        success: function(data){
            dataFunc(data);
        }
    });

}

export {getPatientById, getFullGroupedCatalog,getAllTestByPatientId,getAllTestResultsByTestId,deleteTestById,saveTest}