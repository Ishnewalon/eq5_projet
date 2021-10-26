<template>
  <div className=" p-3 mt-5 border-left border-right border-light">
    <div className="row">
      <div className="col-12 col-sm-6">
        <div className="d-flex justify-content-center align-items-center flex-column p-3 shadow h-100">
        <h4 className="p-2 rounded bg-secondary fw-bold text-white">{dto.firstName + ', ' + dto.lastName} </h4>
        <div className="d-flex justify-content-center align-items-center badge bg-primary text-wrap h2">
        <AiOutlineFile />
        <a onClick={openFile} target="_blank" className="ms-2 text-white">{dto.fileName}</a>
      </div>
    </div>
  </div>
  <div className="col-12 col-sm-6">
    <PreviewOffer  :offer=dto.offerDTO></PreviewOffer>
  </div>
  </div>
  </div>
</template>

<script>
export default {
  name: "PreviewStudent",
  props: ['dto'],
  methods:{
    openFile(){
      const decodedChars = atob(this.dto.file);
      const byteNums = new Array(decodedChars.length);
      for (let i = 0; i < decodedChars.length; i++)
        byteNums[i] = decodedChars.charCodeAt(i);
      let contentType = 'application/pdf';

      if(this.dto.fileName.endsWith('docx'))
        contentType = 'application/vnd.openxmlformats-officedocument.wordprocessingml.document';

      const blob = new Blob([new Uint8Array(byteNums), {type: contentType}]);

      let url = window.URL.createObjectURL(blob);

      const a = document.createElement('a')
      a.href = url
      a.download = this.dto.fileName;
      a.click();
      URL.revokeObjectURL(url)
      alert('téléchargé')
    }
  }
}
</script>

<style scoped>

</style>
