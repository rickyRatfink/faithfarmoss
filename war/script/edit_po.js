$(document).ready(function() {
  item_handler = new ItemHandler();
});

function ItemHandler(opts) {
  var self = this;
  $.extend(self, {}, opts || {});
  self.init();
}

ItemHandler.prototype = {
  template: null,
  $items: null,
  $items_div: null,
  theObject: null,
  
  addItem: function(obj) {
    var self = this;
    var idx;
    obj.total = obj.quantity * obj.price;
    self.theObject.items.push(obj);
    idx = self.theObject.items.indexOf(obj);
    self.theObject.items[idx].idx = idx; 
    self.updateStrObject();
    self.draw();
  },
  
  draw: function() {
    var self = this;
    self.$items_div.html(self.template(self.theObject));
  },
  
  init: function() {
    var self = this;
    self.$items = $("#items");
    self.$items_div = $("#items-div");
    self.template = Handlebars.compile($("#items-template").html());
    self.theObject = $.parseJSON(self.$items.html());
    self.$items_div.on('click', '.add-item', function(e) {
      $.fancybox({
        type: 'iframe',
        href: this.href,
        width: 400,
        height: 350,
        maxHeight: 350
      });
      return false;
    });
    self.$items_div.on('click', '.del-item', function(e) {
      self.theObject.items.splice($(this).data('idx'), 1);
      self.updateStrObject();
      self.draw();
      return false;
    });
    self.registerHelpers();
    self.draw();
  },
  
  registerHelpers: function() {
    var self = this;
    Handlebars.registerHelper('formatCurrency', function(item) {
      return "$" + parseFloat(item).toFixed(2);
    });
  },
  
  updateStrObject: function() {
    var self = this;
    self.updateTotal();
    var str = JSON.stringify(self.theObject);
    self.$items.html(str);
  },
  
  updateTotal: function() {
    var self = this;
    var total = 0;
    for(var i = 0;i < self.theObject.items.length; i++) {
      total += self.theObject.items[i].quantity * self.theObject.items[i].price;
    }
    self.theObject.total = total;
  }
};