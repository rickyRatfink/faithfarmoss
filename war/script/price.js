PriceChecker = function(opts) {
  var self = this;
  $.extend(self, {}, opts || {});
  self.init();
};

PriceChecker.prototype = {
  $search_btn: null,
  $item: null,
  $manufacturer: null,
  $results: null,
  template: null,
  spinner: null,
  
  
  bindEvents: function() {
    var self = this;
    
    self.$item.keypress(function(e) {
      if(e.keyCode == 13) {
        self.$search_btn.trigger('click');
      }
    });
    
    self.$search_btn.click(function(e) {
      var val = self.$item.val();
      self.$item.val('');
      self.$search_btn.attr('disabled', true);
      self.$results.html('');
      self.spinner.spin(self.$results[0]);
      
      var deferred = $.ajax({
        url: '/price',
        type: 'POST',
        dataType: 'json',
        data: {
          action: 'query',
          item: val,
          manufacturer: self.$manufacturer.val()
        }
      });
      
      
      deferred.done(function(data) {
        if(data.success) {
          self.$results.html(self.template(data));
        }
      });
      
      deferred.fail(function(xhr, status) {
        
      });
      
      deferred.always(function() {
        self.$search_btn.attr('disabled', false);
        self.spinner.stop();
      });
      
    });
  },
  
  init: function() {
    var self = this;
    self.template = Handlebars.compile($("#results_template").html());
    self.spinner = new Spinner({
      top: '10px'
    });
    self.$search_btn = $("#search_btn");
    self.$item = $("#item");
    self.$manufacturer = $("#manufacturer");
    self.$results = $("#results");
    self.bindEvents();
    self.$item.focus();
  }
};

$(document).ready(function() {
  pc = new PriceChecker();
});