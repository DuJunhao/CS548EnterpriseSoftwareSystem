
import schema namespace p="http://www.example.org/schemas/clinic/patient" at "Patient.xsd";
import schema namespace t="http://www.example.org/schemas/clinic/Treatment" at "Treament.xsd";
import schema namespace pr="http://www.example.org/schemas/clinic/provider" at "Provider.xsd";
import schema namespace c="http://www.example.org/clinic" at "Clinic.xsd";
declare namespace ph="http://www.example.org/schemas/clinic/providerHistory";
declare function local:getProviderInfo($database as element(c:Clinic))
as item()*
{
    for $provider in $database/pr:providers
    where $provider/pr:ProviderId
    for $treatment in $database/p:patients/p:treatments/p:treatment/node()
    where $treatment/../t:ProviderId=$provider/pr:ProviderId
    return typeswitch($treatment)
    case $Drug as element(t:DrugTreatment)
    return
    <ph:providerHistory> 
    <ph:providerId>{$provider/pr:ProviderId/text()}</ph:providerId>
    <ph:providerName>{$provider/pr:name/text()}</ph:providerName>
    <ph:pid>{$treatment/../../../p:PatientId/text()}</ph:pid>
    <ph:treatment>drugTreatment</ph:treatment>
    </ph:providerHistory>
    case $Surgery as element(t:Surgery)
    return
    <ph:providerHistory> 
    <ph:providerId>{$provider/pr:ProviderId/text()}</ph:providerId>
    <ph:providerName>{$provider/pr:name/text()}</ph:providerName>
    <ph:pid>{$treatment/../../../p:PatientId/text()}</ph:pid>
    <ph:treatment>surgery</ph:treatment>
    </ph:providerHistory>
    case $Radiology as element(t:Radiology )
    return
    <ph:providerHistory> 
    <ph:providerId>{$provider/pr:ProviderId/text()}</ph:providerId>
    <ph:providerName>{$provider/pr:name/text()}</ph:providerName>
    <ph:pid>{$treatment/../../../p:PatientId/text()}</ph:pid>
    <ph:treatment>radiology</ph:treatment>
    </ph:providerHistory>
    default
    return ""
};

let $clinic :=doc("ClinicData.xml")/c:Clinic
 return <ph:drug>{local:getProviderInfo($clinic)}</ph:drug>