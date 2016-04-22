SU ÜRÜNLERİ HAL DEPO GİRİŞ FİŞİ
Firma Adi: ${companyName}
Tarih: ${Date}
Fis No: ${VoucherNo}
Parti No: ${PartNo}
<#assign x = "${Units} ${unitType}" >  <#-- create variable x -->
<#assign y = "Ürün" >

${y?right_pad(10, " ")} ${"Miktar"?right_pad(10, " ")} ${"Depo"?right_pad(20, " ")}
${product?right_pad(10, " ")} ${x?right_pad(10," ")}  ${DepotName?right_pad(20, " ")}

${"Teslim Eden"?right_pad(20, " ")} ${"Teslim Alan"?right_pad(20, " ")}
${TeslimEden?right_pad(20, " ")} ${TeslimAlan?right_pad(20, " ")}
