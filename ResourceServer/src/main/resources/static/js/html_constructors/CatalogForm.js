export let catalogData = {

}


export default function catalogToForm(catalog){
    //console.log(catalog);
    for(var pType in catalog){
  
      catalog[pType].forEach(param => {
        catalogData[param.additionalName] = {
          "id":param.id,
          "min":param.refMin,
          "max":param.refMax,

        }
          //console.log(param.name);
          var paramCard= `
          <div class="parameter-card">
            <input type="number" id="#param-id-${param.id} "class="id-placeholder" value="${param.id}"><br> 
            <div>${param.name} (${param.additionalName}), ${param.unit}</div>
            <div class="line">min: ${param.refMin} max: ${param.refMax} </div>
            <input type="number" id="result-for-param-${param.id}" class="param-input" value="${0}"><br> 
          </div>
          `
          $(`.${pType}`).append(paramCard);
      }); 
    }
    addIndexies();
    
  $(`.results-area`).trigger("testsfilled");
    
  }
  function addIndexies(){
    addIndexLine(11, 12, "Соотношение CD3+CD4+ / CD3+CD8+");
    addIndexLine(20, 21, "CD3+TNFa+ (стим) / CD3+TNFa+ (спон)");
    addIndexLine(22, 23,"CD3+IL2+ (стим) / CD3+IL2+ (спон)");

  }
  function addIndexLine(firstFieldId, secndFieldId, name){
    let first = $(`#result-for-param-${firstFieldId}`).val();
    let second = $(`#result-for-param-${secndFieldId}`).val();
    let res = second==0 ? 0 : (first / second).toFixed(3);
    var paramCard= `
    <div class="parameter-card"> 
      <br><div>${name}</div>
      <input type="number" id="i${firstFieldId}-i${secndFieldId}" class="index-line param-input" value="${res}" disabled ><br> 
    </div>`

    $(`.Indexies`).append(paramCard);

    $(`#result-for-param-${firstFieldId}`).on("change", ()=>{
      $(`#i${firstFieldId}-i${secndFieldId}`).trigger("indexiesChanged");
    });
    
    $(`#result-for-param-${secndFieldId}`).on("change", ()=>{
      $(`#i${firstFieldId}-i${secndFieldId}`).trigger("indexiesChanged");
    });

    $(`#i${firstFieldId}-i${secndFieldId}`).on("indexiesChanged",()=>{
      $(`#i${firstFieldId}-i${secndFieldId}`).val(devideFieldVals(firstFieldId,secndFieldId));

    })

  }


  function devideFieldVals(firstFieldId, secndFieldId){
    let first = $(`#result-for-param-${firstFieldId}`).val();
    let second = $(`#result-for-param-${secndFieldId}`).val();
    return second==0 ? 0 : first / second;
  }