import { Component, Input, OnInit, ViewChild, ViewEncapsulation } from '@angular/core';
import { FormControl } from '@angular/forms';
import { QuillConfiguration } from './quill-configuration';
import 'quill-emoji/dist/quill-emoji.js';

import Quill from 'quill'

import BlotFormatter from 'quill-blot-formatter/dist/BlotFormatter';
import { QuillEditorComponent, QuillViewComponent } from 'ngx-quill';
Quill.register('modules/blotFormatter', BlotFormatter);

@Component({
  selector: 'app-rich-text-editor',
  templateUrl: './rich-text-editor.component.html',
  styleUrls: ['./rich-text-editor.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class RichTextEditorComponent implements OnInit {
  modules = QuillConfiguration; // TODO: use based on the requirement
  @ViewChild(QuillEditorComponent, { static: false })
  quillEditorComponent: QuillEditorComponent;

  @ViewChild(QuillViewComponent, { static: false })
  quillViewComponent: QuillViewComponent;
  @Input() control: FormControl;

  editorStyle = {
    'min-height': '200px',
  };

  constructor() { }

  ngOnInit() {
    this.control = this.control ?? new FormControl();
  }
}
