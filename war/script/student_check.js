$(document).ready(function() {
  window.thespin = new Spinner({
    left: 0,
    top: 2,
    radius: 5,
    length: 4,
    speed: 1.4,
    lines: 11,
    width: 3,
    color: '#2a4480'
    
  });
  $("input[name=firstName], input[name=lastName]").change(function(e) {
    var $first;
    var $last;
    if($(this).attr('name') == "firstName") {
      $first = $(this);
      $last = $("input[name=lastName]");
    } else {
      $last = $(this);
      $first = $("input[name=firstName]");
    }
    if($first.val().length > 0 && $last.val().length > 0) {
      
      $("#checkSpinner").html('<div style="margin-left:30px;padding-top:7px;">Checking for person in legacy intake system...</div>');
      thespin.spin($("#checkSpinner")[0]);
      var def = $.ajax({
        url: '/student_check',
        type: 'POST',
        dataType: 'json',
        data: {
          first: $first.val(),
          last: $last.val()
        }
      });
      
      def.done(function(data) {
        thespin.stop();
        $("#checkSpinner").html('');
        if(data.success) {
          if(data.result) {
            $("#checkSpinner").html('<div style="margin-left:30px;padding-top:7px;">This person <strong>was</strong> found in legacy intake system.</div>');
          } else {
            $("#checkSpinner").html('<div style="margin-left:30px;padding-top:7px;">This person <strong>was not</strong> found in legacy intake system.</div>');
          }
        }
      });
    }
  });
});