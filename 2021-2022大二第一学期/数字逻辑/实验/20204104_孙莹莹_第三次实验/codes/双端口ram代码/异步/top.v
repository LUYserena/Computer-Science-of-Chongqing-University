`timescale 1ns / 1ps
//////////////////////////////////////////////////////////////////////////////////
// Company: 
// Engineer: 
// 
// Create Date: 2021/11/22 16:29:35
// Design Name: 
// Module Name: top
// Project Name: 
// Target Devices: 
// Tool Versions: 
// Description: 
// 
// Dependencies: 
// 
// Revision:
// Revision 0.01 - File Created
// Additional Comments:
// 
//////////////////////////////////////////////////////////////////////////////////


module top(clk,we1,oe1,addra,din_a,dout_a,we2,oe2,addrb,din_b,dout_b,sm_duan,sm_wei);
    input clk;
    input we1,oe1;
    input[3:0] addra;
    input[1:0] din_a;
    output [1:0] dout_a;
    
    input we2,oe2;
    input[3:0] addrb;
    input[1:0] din_b;
    output [1:0] dout_b;
    
     output[3:0]sm_wei;
     output[7:0]sm_duan;
     wire [1:0]data_a;
     wire [1:0]data_b;
     assign data_a = dout_a;
     assign data_b = dout_b;
    dual_asy rams(clk,we1,oe1,addra,din_a,dout_a,we2,oe2,addrb,din_b,dout_b);
    show demo(clk,data_a,data_b,sm_wei,sm_duan);
endmodule