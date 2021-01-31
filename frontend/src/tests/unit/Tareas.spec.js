import { mount } from '@vue/test-utils'
import { expect } from 'chai'
import Vue from 'vue'
import Tareas from '@/components/Tareas.vue'

describe("Tareas", () => {
  
    it("Deberia hacer alguna wea", () => {
        const wrapper = mount(Tareas)
        expect(wrapper.find('.count')).to.equal(0)
    });
  });