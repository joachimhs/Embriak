Embriak.Model = Ember.Object.extend();

Embriak.Model.reopenClass({
    find: function(url, id, type, key) {
        console.log('find: ' + type + " id: " + id + " url: " + url + "/" + id);
        var foundItem = this.contentArrayContains(id, type);

        if (!foundItem) {
            console.log('!foundItem: ' + foundItem);
            foundItem = type.create({ id: id, isLoaded: false});
            $.getJSON(url + "/" +  id, function(data) {
                console.log(data);
                if (data[key]) {
                    foundItem.setProperties(data[key]);
                    foundItem.set('isLoaded', true);
                    foundItem.set('isError', false);
                }
            });
            Ember.get(type, 'collection').pushObject(foundItem);
        }

        console.log('found: ' + foundItem.get('id'));
        return foundItem;
    },

    contentArrayContains: function(id, type) {
        var contains = null;

        Ember.get(type, 'collection').forEach(function(item) {
            if (item.get('id') === id) {
                contains = item;
            }
        });

        return contains;
    },

    findAll: function(url, type, key) {
        console.log('findAll: ' + type + " " + url + " " + key);

        var collection = this;
        Ember.get(type, 'collection').set('isLoaded', false);
        $.getJSON(url, function(data) {
            $.each(data[key], function(i, row) {
                var item = null;

                if (typeof row === "string") {
                    item = type.create({id: row});
                    Ember.get(type, 'collection').pushObject(item);
                } else {
                    item = collection.contentArrayContains(row.id, type);

                    if (!item) {
                        item =  type.create();
                        Ember.get(type, 'collection').pushObject(item);
                    }
                    item.setProperties(row);
                }

                item.set('isLoaded', true);
                item.set('isError', false);
            });
            Ember.get(type, 'collection').set('isLoaded', true);
        });

        return Ember.get(type, 'collection');
    },

    delete: function(url, type, id) {
        console.log('delete: ' + type + " " + id);
        var collection = this;
        $.ajax({
            type: 'DELETE',
            url: url + "/" + id,
            success: function(res, status, xhr) {
                if(res.deleted) {
                    var item = collection.contentArrayContains(id, type);
                    if (item) {
                        Ember.get(type, 'collection').removeObject(item);
                    }
                }
            },
            error: function(xhr, status, err) { alert('Unable to delete: ' + status + " :: " + err); }
        });
    },

    createRecord: function(url, type, model) {
        console.log('save: ' + type + " " + model.get('id'));
        var collection = this;
        model.set('isSaving', true);
        $.ajax({
            type: "PUT",
            url: url,
            data: JSON.stringify(model),
            success: function(res, status, xhr) {
                if (res.submitted) {
                    Ember.get(type, 'collection').pushObject(model);
                    model.set('isSaving', false);
                } else {
                    model.set('isError', true);
                }
            },
            error: function(xhr, status, err) { model.set('isError', false);  }
        });
    },

    updateRecord: function(url, type, model) {
        console.log('update: ' + type + " " + model.get('id'));
        var collection = this;
        model.set('isSaving', true);
        console.log(JSON.stringify(model));
        $.ajax({
            type: "PUT",
            url: url,
            data: JSON.stringify(model),
            success: function(res, status, xhr) {
                if (res.id) {
                    model.set('isSaving', false);
                    model.setProperties(res);
                } else {
                    model.set('isError', true);
                }
            },
            error: function(xhr, status, err) { model.set('isError', false);  }
        })
    }
});

Embriak.BucketModel = Embriak.Model.extend({
    keys: function() {
        var keyObject = [];
        console.log(this.get('id'));
        $.getJSON("/riak/" + encodeURIComponent(this.get('id')) + "?props=false&keys=true", function(data) {
            $.each(data["keys"], function(index, row) {
                keyObject.pushObject(Ember.Object.create({id: row}));
            });

            console.log(data);
        });

        return keyObject;
    }.property('id')
});

Embriak.BucketModel.reopenClass({
    collection: Ember.A(),

    find: function(id) {
        return Embriak.Model.find("/riak", id, Embriak.BucketModel, "buckets");
    },


    findAll: function() {
        return Embriak.Model.findAll("/riak?buckets=true", Embriak.BucketModel, "buckets");
    }
});

Embriak.KeyValueModel = Embriak.Model.extend({

});

Embriak.KeyValueModel.reopenClass({
    collection: Ember.A(),


    findAll: function(id) {
        console.log('Embriak.KeyValueModel findAll(' + id + ");");
        return Embriak.Model.findAll('/riak/'+ id +'?props=false&keys=true', Embriak.KeyValueModel, "keys");
    }
});